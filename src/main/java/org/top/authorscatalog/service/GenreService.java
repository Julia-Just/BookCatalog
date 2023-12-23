package org.top.authorscatalog.service;

import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.Genre;

import java.util.Optional;

// Сервис для работы с жанрами
@Service
public interface GenreService {

    // получить все жанры
    Iterable<Genre> findAll();

    // получить жанр по id
    Optional<Genre> findById(Integer id);

    // добавить жанр
    Optional<Genre> save(Genre genre);

    // удалить жанр
    Optional<Genre> deleteById(Integer id);

    // обновить жанр
    Optional<Genre> update(Genre genre);
}
