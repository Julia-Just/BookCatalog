package org.top.authorscatalog.service;

import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.Author;

import java.util.Optional;

// Сервис для работы с авторами
@Service
public interface AuthorService {

    // получить всех авторов
    Iterable<Author> findAll();

    // получить автора по id
    Optional<Author> findById(Integer id);

    // добавить автора
    Optional<Author> save(Author author, Boolean forceSave);

    // удалить автора
    Optional<Author> deleteById(Integer id);

    // обновить автора
    Optional<Author> update(Author author);
}
