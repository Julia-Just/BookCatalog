package org.top.authorscatalog.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.authorscatalog.entity.Client;

// ClientRepository - репозиторий для работы с клиентами
@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

}
