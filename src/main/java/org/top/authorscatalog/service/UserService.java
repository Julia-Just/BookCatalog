package org.top.authorscatalog.service;

import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.User;
import org.top.authorscatalog.form.UserRegistrationForm;

import java.util.Optional;

// UserService - сервис для работы с пользователями
@Service
public interface UserService {

    // регистрация пользователя
    boolean register(UserRegistrationForm userRegistrationForm);

    Iterable<User> findUsersByRole(String role);

    Optional<User> findUserByLogin(String login);

    Optional<User> findById(Integer id);
}
