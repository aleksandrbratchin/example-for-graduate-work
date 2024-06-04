package ru.skypro.homework.service;

import jakarta.transaction.Transactional;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

import java.util.List;

public interface AdServiceApi {

    /**
     * Получение всех объявлений
     *
     * @return List {@link Ad}  список объявлений
     */
    List<Ad> getAllAds();

    /**
     * Добавление объявления
     *
     * @param ad    {@link Ad} для сохранения объявления
     * @param image {@link Image} для сохранения картинки объявления
     * @return {@link Ad} сохраненное объявление
     */
    @Transactional
    Ad createAd(Ad ad, Image image);

    /**
     * Поиск объявления по идентификатору
     *
     * @param id идентификатор объявления
     * @return {@link Ad} искомое объявление
     */

    Ad findById(Long id);

    /**
     * Удаление объявления по идентификатору
     *
     * @param id идентификатор объявления
     */

    void deleteAd(Long id);

    /**
     * Обновление объявления
     *
     * @param id         идентификатор объявления
     * @param properties {@link CreateOrUpdateAd}
     * @return {@link Ad} измененное объявление
     */

    Ad updateAd(Long id, CreateOrUpdateAd properties);

    /**
     * Получение всех объявлений одного пользователя
     *
     * @param user {@link User}
     * @return List {@link Ad} список объявлений данного пользователя
     */

    List<Ad> getAdsByUser(User user);

    /**
     * Обновление картинки объявления
     *
     * @param id    идентификатор объявления
     * @param image {@link Image} картинка
     * @return {@link Ad} обновленное объявление
     */

    @Transactional
    Ad updateImageAd(Long id, Image image);
}
