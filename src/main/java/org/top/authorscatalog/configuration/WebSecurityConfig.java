package org.top.authorscatalog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.top.authorscatalog.rdb.repository.UserRepository;
import org.top.authorscatalog.rdb.security.RdbUserDetailsService;

import javax.sql.DataSource;

// WebSecurityConfig - конфигурация Spring Security
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public final UserRepository userRepository;
    private final DataSource dataSource;

    public WebSecurityConfig(UserRepository userRepository, DataSource dataSource) {
        this.userRepository = userRepository;
        this.dataSource = dataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // конфигурация реализуется через паттерн Строитель (Builder)
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/",
                                "/user/register",
                                "/author",
                                "/publisher",
                                "/book",
                                "/book/*",
                                "/genre",
                                "webjars/**"
                        ).permitAll()
                        .requestMatchers("/review/add/*").authenticated()
                        .requestMatchers(
                                "/*/new",
                                "/*/delete",
                                "/*/delete/*",
                                "/*/update",
                                "/*/update/*")
                        .hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").permitAll().successForwardUrl("/"))
                .logout(customizer -> customizer.logoutSuccessUrl("/login"));
        return http.build();
    }

    // Зависимость кодировщика паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*******************************************************/
    /*** Конфигурация UserDetailsService для работы с БД ***/
    /*******************************************************/

    @Bean
    public UserDetailsService userDetailsService() {
        return new RdbUserDetailsService(userRepository);
    }

    // зависимости, необходимые для работы Spring Security с нашими сервисами пользователя
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public UserDetailsManager users(HttpSecurity http) throws Exception {
        // TODO: remove deprecate calls
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
        return jdbcUserDetailsManager;
    }



//    // Зависимость UserDetailsService - версия для тестирования
//    // (in memory develop only version)
//    @Bean
//    public UserDetailsService userDetailsService() {
//        // ЗАГЛУШКА (ПОЗЖЕ ВМЕСТО ЭТОГО БУДЕТ RdbUserDetailsService)
//        // сконфигурируем пользователей в контексте ОЗУ
//
//        // 1. создать пользователей
//        UserDetails user1 = User.builder()
//                .username("user1")
//                .password("qwerty")
//                .passwordEncoder(passwordEncoder()::encode)
//                .build();
//        UserDetails user2 = User.builder()
//                .username("user2")
//                .password("qwerty")
//                .passwordEncoder(passwordEncoder()::encode)
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("admin")
//                .passwordEncoder(passwordEncoder()::encode)
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2, admin);
//    }
}
