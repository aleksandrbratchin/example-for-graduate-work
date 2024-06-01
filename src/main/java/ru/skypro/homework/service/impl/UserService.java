package ru.skypro.homework.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.security.UserPrincipal;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.exception.IncorrectCurrentPasswordException;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    /**
     * Поиск пользователя по логину
     * @param username логин пользователя
     * @return {@link User}
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    /**
     * Сохранить нового пользователя
     * @param user пользователь которого надо сохранить {@link User}
     * @return {@link User}
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Обновить аватар пользователя
     */
    public void setAvatar(User user, Image image) {
        if(user.getAvatar() != null){
            image.setId(user.getAvatar().getId());
        }
        user.setAvatar(image);
        userRepository.save(user);
    }

    /**
     * Обновить данные о пользователе
     * @param user пользователь которого надо сохранить {@link UpdateUser}
     * @return {@link User}
     */
    public User update(User user, UpdateUser updateUser) {
        user.setPhone(updateUser.getPhone());
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        return userRepository.save(user);
    }

    /**
     * Обновить пароль пользователя
     */
    public void updatePassword(UserPrincipal userPrincipal, NewPassword password) {
        User user = userPrincipal.getUser();
        if (!encoder.matches(password.getCurrentPassword(), user.getPassword())) {
            throw new IncorrectCurrentPasswordException("Текущий пароль неверен");
        }
        user.setPassword(encoder.encode(password.getNewPassword()));
        userRepository.save(user);
    }

}
