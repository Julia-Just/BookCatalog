package org.top.authorscatalog.entity;

import jakarta.persistence.*;

// Client описывает сущность "Клиент" - запись таблицы клиентов
@Entity
@Table(name = "client_t")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_f", nullable = false)
    private String name;          // имя клиента

    @Column(name = "email_f", nullable = false)
    private String email;          // электронный адрес клиента

    // конструкторы

    public Client() {
        id = 0;
        name = "";
        email = "";
    }

    // геттеры и сеттеры

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // toString

    @Override
    public String toString() {
        return "id=" + id + "Клиент: " + name + ", email:" + email;
    }
}
