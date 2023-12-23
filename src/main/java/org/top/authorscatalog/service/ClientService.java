package org.top.authorscatalog.service;

import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.Client;

import java.util.Optional;

// Сервис для работы с клиентами
@Service
public  interface ClientService {

    // получить всех клиентов
    Iterable<Client> findAll();

    // получить клиента по id
    Optional<Client> findById(Integer id);

    // добавить клиента
    Optional<Client> save(Client client);

    // удалить клиента
    Optional<Client> deleteById(Integer id);

    // обновить клиента
    Optional<Client> update(Client client);
}
