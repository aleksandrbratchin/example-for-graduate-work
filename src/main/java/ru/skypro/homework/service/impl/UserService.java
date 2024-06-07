package ru.skypro.homework.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.exception.IncorrectCurrentPasswordException;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserServiceApi;

@Service
@Log4j2
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"userCache"})
public class UserService implements UserServiceApi {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Cacheable(key = "#username")
    public User findByUsername(String username) {
        log.info("Поиск пользователя по имени пользователя: {}", username);
        return userRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("Пользователь с именем пользователя {} не найден", username);
                    return new UsernameNotFoundException(
                            "Нет пользователя с таким именем пользователя \"" + username + "\""
                    );
                }
        );
    }

    @Override
    @CachePut(key = "#user.username")
    @Transactional
    public User save(User user) {
        log.info("Сохранение пользователя: {}", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    @CacheEvict(key = "#user.username")
    public void updateUserAvatar(User user, Image image) {
        log.info("Обновление аватара для пользователя: {}", user.getUsername());
        if (user.getAvatar() != null) {
            image.setId(user.getAvatar().getId());
        }
        user.setAvatar(image);
        save(user);
    }

    @Override
    @CachePut(key = "#user.username")
    public User updateUserDetails(User user, UpdateUser updateUser) {
        log.info("Обновление данных для пользователя: {}", user.getUsername());
        user.setPhone(updateUser.getPhone());
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        return save(user);
    }

    @Override
    @CacheEvict(key = "#user.username")
    public void changeUserPassword(User user, NewPassword password) {
        log.info("Изменение пароля для пользователя: {}", user.getUsername());
        if (!encoder.matches(password.getCurrentPassword(), user.getPassword())) {
            log.error("Неверный текущий пароль для пользователя: {}", user.getUsername());
            throw new IncorrectCurrentPasswordException("Текущий пароль неверен");
        }
        user.setPassword(encoder.encode(password.getNewPassword()));
        save(user);
    }

}
