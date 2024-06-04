package ru.skypro.homework.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.exception.IncorrectCurrentPasswordException;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements ru.skypro.homework.service.UserServiceApi {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Нет пользователя с таким логином \"" + username + "\"")
        );
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void setAvatar(User user, Image image) {
        if (user.getAvatar() != null) {
            image.setId(user.getAvatar().getId());
        }
        user.setAvatar(image);
        userRepository.save(user);
    }

    @Override
    public User update(User user, UpdateUser updateUser) {
        user.setPhone(updateUser.getPhone());
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        return userRepository.save(user);
    }

    @Override
    public void updatePassword(User user, NewPassword password) {
        if (!encoder.matches(password.getCurrentPassword(), user.getPassword())) {
            throw new IncorrectCurrentPasswordException("Текущий пароль неверен");
        }
        user.setPassword(encoder.encode(password.getNewPassword()));
        userRepository.save(user);
    }

}
