package org.top.authorscatalog.rdb;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.User;
import org.top.authorscatalog.form.UserRegistrationForm;
import org.top.authorscatalog.rdb.repository.UserRepository;
import org.top.authorscatalog.service.UserService;

import java.util.Optional;

// RdbUserService - бизнес-логика работы с пользоваталями
@Service
public class RdbUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RdbUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(UserRegistrationForm userRegistrationForm) {
        // бизнес-логика регистрации пользователя

        // 1. проверить подтверждение пароля
        if (!userRegistrationForm.getPassword().equals(userRegistrationForm.getPasswordConfirmation())) {
            return false;
        }
        // 2. проверить, уникален ли логин

        if (userRepository.findByLogin(userRegistrationForm.getLogin()).isPresent()) {
            return false;
        }
        // 3. если всё - ок, то сохранить
        // 3.1. создаем пользователя
        User user = new User();
        user.setLogin(userRegistrationForm.getLogin());
        String passwordHash = passwordEncoder.encode(userRegistrationForm.getPassword());
        user.setPassword(passwordHash);
        user.setRole(userRegistrationForm.getRole());
        userRepository.save(user);
        return true;
    }

    @Override
    public Iterable<User> findUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }
}
