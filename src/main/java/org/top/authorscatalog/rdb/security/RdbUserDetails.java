package org.top.authorscatalog.rdb.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.top.authorscatalog.entity.User;

import java.util.Collection;
import java.util.Collections;

// RdbUserDetails - имплементация интерфейса UserDetails - интерфейс для работы с сущностью пользователя
// не является частью IoC-контейнера, т.к. UserDetails - это интерфейс сущности, а не сервиса
public class RdbUserDetails implements UserDetails {

    // выгруженный из БД пользователь
    private User user;

    // конструктор

    public RdbUserDetails(User user) {
        this.user = user;
    }

    // методы интерфейса

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getRole();       // конвертируем роль пользователя из БД в роль Spring Security
        return Collections
                .<GrantedAuthority>singletonList(
                        new SimpleGrantedAuthority("ROLE_" + role)
                );
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // геттеры и сеттеры

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
