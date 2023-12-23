package org.top.authorscatalog.entity;

import jakarta.persistence.*;

import java.util.Set;

// Author описывает сущность "Автор" - запись таблицы авторов
@Entity
@Table(name = "author_t")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "last_name_f", nullable = false)
    private String lastName;          // фамилия автора

    @Column(name = "first_name_f", nullable = false)
    private String firstName;         // имя автора

    @Column(name = "patronymic_f")
    private String patronymic;        // отчество автора

    @Column(name = "biography_f")
    private String biography;         // биография автора

    // Связи

    @OneToMany(mappedBy = "author")
    private Set<BookAuthor> bookAuthorSet;

    // конструкторы

    public Author() {
        id = 0;
        lastName = "";
        firstName = "";
        patronymic = null;
        biography = null;
        bookAuthorSet = null;
    }

    // геттеры и сеттеры

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Set<BookAuthor> getBookAuthorSet() {
        return bookAuthorSet;
    }

    public void setBookAuthorSet(Set<BookAuthor> bookAuthorSet) {
        this.bookAuthorSet = bookAuthorSet;
    }

    // toString

    @Override
    public String toString() {
        return "''" + id + ": " + lastName + " " + firstName + " " + patronymic + " (" + biography + ")''";
    }
}
