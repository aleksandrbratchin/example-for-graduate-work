package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
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

    public User findById(Long idUser) {
        return userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("Нет пользователя с id = " + idUser));
    }

    public void setAvatar(User user, Image image) {
        user.setAvatar(image);
        userRepository.save(user);
    }

}