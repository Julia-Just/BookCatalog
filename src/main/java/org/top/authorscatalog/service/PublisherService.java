package org.top.authorscatalog.service;

import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.Publisher;

import java.util.Optional;

// Сервис для работы с издательствами
@Service
public interface PublisherService {

    // получить все издательства
    Iterable<Publisher> findAll();

    // получить издательство по id
    Optional<Publisher> findById(Integer id);

    // добавить издательство
    Optional<Publisher> save(Publisher publisher);

    // удалить издательство
    Optional<Publisher> deleteById(Integer id);

    // обновить издательство
    Optional<Publisher> update(Publisher publisher);
}
