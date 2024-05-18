package ru.skypro.homework.service;

import ru.skypro.homework.model.User;

public interface UserServiceApi {

    /**
     * Поиск пользователя по логину
     * @param username логин пользователя
     * @return {@link User}
     */
    User findByUsername(String username);

    /**
     * Сохранить нового пользователя
     * @param user пользователь которого надо сохранить {@link User}
     * @return {@link User}
     */
    User save(User user);
}
