package org.top.authorscatalog.service;

import org.top.authorscatalog.entity.Review;

import java.util.Optional;

// Сервис для работы с отзывами
public interface ReviewService {

    // 1. Получение всех отзывов по книге
    Iterable<Review> findAllByBook(Integer bookId);

    // 2. Получение отзыва по id
    Optional<Review> findById(Integer id);

    // 3. Добавление отзыва
    Review save(Review review);

    // 4. Редактирование - разрешается только в течение недели после написания
    // само редактирование не меняет дату написания
    Optional<Review> update(Review review);

    // 5. Удаление отзыва по id
    Optional<Review> deleteById(Integer id);
}
