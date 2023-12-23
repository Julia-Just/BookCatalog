package org.top.authorscatalog.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.authorscatalog.entity.Book;

// BookRepository - репозиторий для работы с книгами
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

}
