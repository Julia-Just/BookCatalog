package org.top.authorscatalog.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.authorscatalog.entity.User;

import java.util.Optional;

// UserRepository - репозиторий пользователей
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByLogin(String login);
    Iterable<User> findByRole(String role);
}
