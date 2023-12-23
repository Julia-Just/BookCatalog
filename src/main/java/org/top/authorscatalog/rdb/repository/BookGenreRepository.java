package org.top.authorscatalog.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.authorscatalog.entity.BookGenre;

@Repository
public interface BookGenreRepository extends CrudRepository<BookGenre, Integer> {
}
