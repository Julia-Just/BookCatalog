package org.top.authorscatalog.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.authorscatalog.entity.Author;

import java.util.Optional;

// AuthorRepository - репозиторий для работы с авторами
@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {
    Optional<Author> findFirstByLastNameAndFirstNameAndPatronymic(String lastName, String firstName, String patronymic);
}
