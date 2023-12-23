package org.top.authorscatalog.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.authorscatalog.entity.Publisher;

// PublisherRepository - репозиторий для работы с издательствами
@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Integer> {

}
