package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(
            @Qualifier("customUserDetailsService") UserDetailsService userDetailsService,
            AuthenticationManager authenticationManager,
            UserService userService,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.encoder = passwordEncoder;
    }

    @Override
    public boolean login(String userName, String password) {
        try {
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean register(Register register) {
        try {
            userDetailsService.loadUserByUsername(register.getUsername());
        } catch (UsernameNotFoundException e) {
            User user = userMapper.fromRegister(register);
            user.setPassword(encoder.encode(register.getPassword()));
            userService.save(user);
            return true;
        }
        return false;
    }

}
