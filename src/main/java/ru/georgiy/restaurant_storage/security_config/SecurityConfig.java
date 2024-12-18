package ru.georgiy.restaurant_storage.security_config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import ru.georgiy.restaurant_storage.handler.AuthenticationHandler;
import ru.georgiy.restaurant_storage.services.PersonDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private final PersonDetailsService personDetailsService;
    private final AuthenticationHandler authenticationHandler;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, AuthenticationHandler authenticationHandler) {
        this.personDetailsService = personDetailsService;
        this.authenticationHandler = authenticationHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/storage/{id}/edit").hasRole("ADMIN")
                        .requestMatchers("/auth/welcome").hasRole("USER")
                        .requestMatchers("/auth/registration", "/auth/login", "/error").permitAll()
                        .anyRequest().hasAnyRole("OWNER", "ADMIN"))
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .successHandler(authenticationHandler)
                        .failureUrl("/auth/login?error"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login"));


        return http.build();
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
        log.info("env {}", System.getenv("URL"));
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
