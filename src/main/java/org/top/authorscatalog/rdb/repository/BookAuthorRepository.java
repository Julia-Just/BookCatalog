package org.top.authorscatalog.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.authorscatalog.entity.BookAuthor;

@Repository
public interface BookAuthorRepository extends CrudRepository<BookAuthor, Integer> {
}
