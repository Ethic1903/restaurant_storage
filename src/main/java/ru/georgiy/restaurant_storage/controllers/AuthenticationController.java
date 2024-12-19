package ru.georgiy.restaurant_storage.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.georgiy.restaurant_storage.models.Person;
import ru.georgiy.restaurant_storage.security.PersonDetails;
import ru.georgiy.restaurant_storage.services.PersonService;
import ru.georgiy.restaurant_storage.services.RegistrationService;
import ru.georgiy.restaurant_storage.util.PersonValidator;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    private static final String OWNER_KEY = "ownerKey123";
    private static final String ADMIN_KEY = "adminKey123";

    private final PersonService personService;
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    @Autowired
    public AuthenticationController(PersonService personService, RegistrationService registrationService, PersonValidator personValidator) {
        this.personService = personService;
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "authentication/login-page";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "authentication/registration-page";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "authentication/registration-page";
        registrationService.register(person);
        return "redirect:/auth/login";
    }

    @GetMapping("/welcome")
    public String welcomePage() {
        return "authentication/welcome-page";
    }

    @PostMapping("/welcome")
    public String changeRole(@RequestParam("specKey") String key, Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getUser();

        if (OWNER_KEY.equals(key)) {
            personService.refreshRole(person.getFio(), "ROLE_OWNER");
        }
        else if (ADMIN_KEY.equals(key)) {
            personService.refreshRole(person.getFio(), "ROLE_ADMIN");
        }
        else {
            return "authentication/welcome-page";
        }

        return "redirect:/storage";
    }

}
