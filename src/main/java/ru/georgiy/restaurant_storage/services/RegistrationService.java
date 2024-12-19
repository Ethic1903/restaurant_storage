package ru.georgiy.restaurant_storage.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.georgiy.restaurant_storage.models.Person;
import ru.georgiy.restaurant_storage.repositories.PeopleRepository;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        person.setRole("ROLE_USER");
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        peopleRepository.save(person);
    }

    @Transactional
    public void setOwnerRole(Person person) {
        person.setRole("ROLE_OWNER");
        peopleRepository.save(person);
    }

    @Transactional
    public void setAdminRole(Person person) {
        person.setRole("ROLE_ADMIN");
        peopleRepository.save(person);
    }
}
