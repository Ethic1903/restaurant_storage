package ru.georgiy.restaurant_storage.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.georgiy.restaurant_storage.models.Person;
import ru.georgiy.restaurant_storage.repositories.PeopleRepository;
import ru.georgiy.restaurant_storage.security.PersonDetails;

import java.util.List;
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

    public void updateRole(String fio, String newRole) {
        Optional<Person> person1 = peopleRepository.findByFio(fio);
        if (person1.isPresent()) {
            Person person = person1.get();
            person.setRole(newRole);
            peopleRepository.save(person);
        }
        else {
            throw new UsernameNotFoundException("Пользователь с таким именем не найден");
        }
    }

    @Transactional
    public void refreshRole(String fio, String newRole) {
        Person person = peopleRepository.findByFio(fio)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        person.setRole(newRole);
        peopleRepository.save(person);
        List<GrantedAuthority> updatedAuthorities = AuthorityUtils.createAuthorityList(newRole);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                new PersonDetails(person),
                null,
                updatedAuthorities
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

}
