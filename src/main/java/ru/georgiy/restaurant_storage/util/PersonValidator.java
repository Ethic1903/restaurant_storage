package ru.georgiy.restaurant_storage.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.georgiy.restaurant_storage.models.Person;
import ru.georgiy.restaurant_storage.services.PersonDetailsService;
import ru.georgiy.restaurant_storage.services.PersonService;

@Component
public class PersonValidator implements Validator {
    private final PersonDetailsService personDetailsService;
    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService, PersonService personService) {
        this.personDetailsService = personDetailsService;
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personService.checkUniqueUsername(person.getFio()).isPresent())
            errors.rejectValue("fio", "", "Пользователь с таким именем уже существует.");
    }
}
