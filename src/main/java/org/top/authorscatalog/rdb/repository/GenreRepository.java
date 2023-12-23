package org.top.authorscatalog.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.authorscatalog.entity.Genre;

// GenreRepository - репозиторий для работы с жанрами
@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {
}
