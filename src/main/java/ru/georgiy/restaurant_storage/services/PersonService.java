package ru.georgiy.restaurant_storage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.georgiy.restaurant_storage.models.Person;
import ru.georgiy.restaurant_storage.repositories.PeopleRepository;

import java.util.Optional;

@Service
public class PersonService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Optional<Person> checkUniqueUsername(String username) {
        return peopleRepository.findByFio(username);
    }
}
