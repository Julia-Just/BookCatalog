package org.top.authorscatalog.rdb.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.User;
import org.top.authorscatalog.rdb.repository.UserRepository;

import java.util.Optional;

// UserDetailsService - сервис для уатентификации пользователей и получения информации о них
@Service
public class RdbUserDetailsService implements UserDetailsService {

    public final UserRepository userRepository;

    public RdbUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. получить пользователя
        Optional<User> user = userRepository.findByLogin(username);
        // 2. если пользователь не найден, то сообщить об ошибке
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        // 3. иначе вернуть UserDetails
        return new RdbUserDetails(user.get());
    }
}
