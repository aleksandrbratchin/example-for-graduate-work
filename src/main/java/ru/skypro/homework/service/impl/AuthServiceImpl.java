package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;


    public AuthServiceImpl(
            @Qualifier("customUserDetailsService") UserDetailsService userDetailsService,
            UserService userService, UserMapper userMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.encoder = passwordEncoder;
    }

    @Override
    public boolean login(String userName, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        if (userDetails == null) {
            return false;
        }
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {
        if (userDetailsService.loadUserByUsername(register.getUsername()) != null) {
            return false;
        }
        User user = userMapper.fromRegister(register);
        userService.save(user);
        return true;
    }

}
