package ru.skypro.homework.config.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.impl.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private WebApplicationContext applicationContext;

    private UserService userService;

    @PostConstruct
    public void completeSetup() {
        userService = applicationContext.getBean(UserService.class);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final User user = userService.findByUsername(username);
        return new UserPrincipal(user);
    }
}
