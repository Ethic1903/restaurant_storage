package ru.georgiy.restaurant_storage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.georgiy.restaurant_storage.models.Person;
import ru.georgiy.restaurant_storage.repositories.PeopleRepository;
import ru.georgiy.restaurant_storage.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository userRepository) {
        this.peopleRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> user = peopleRepository.findByFio(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден.");
        }
        return new PersonDetails(user.get());
    }
}
