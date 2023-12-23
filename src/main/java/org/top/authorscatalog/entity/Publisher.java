package org.top.authorscatalog.entity;

import jakarta.persistence.*;

import java.util.Set;

// Publisher описывает сущность "Издательство" - запись таблицы издательств
@Entity
@Table(name = "publisher_t")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_f", nullable = false)
    private String name;          // Наименование издательства

    @Column(name="address_f")
    private String address;       // Адрес издательства

    // Связи

    @OneToMany(mappedBy = "publisher")
    private Set<Book> books;

    // конструкторы

    public Publisher() {
        id = 0;
        name = "";
        address = null;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Book> getBookSet() {
        return books;
    }

    public void setBookSet(Set<Book> bookSet) {
        this.books = books;
    }

// toString

    @Override
    public String toString() {
        return id + ": " + "''" + name + "''" + " - " + address;
    }
}
