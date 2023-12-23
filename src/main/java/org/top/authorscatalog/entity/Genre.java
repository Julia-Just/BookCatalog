package org.top.authorscatalog.entity;

import jakarta.persistence.*;

import java.util.Set;

// класс описывает сущность "Жанр" - запись таблицы жанров
@Entity
@Table(name = "genre_t")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "genre_name_f", nullable = false)
    private String name;             // Название жанра

    @Column(name = "genre_description_f")
    private String description;     // Описание жанра

    // Связи

    @OneToMany(mappedBy = "genre")
    private Set<BookGenre> bookGenreSet;

    // конструкторы

    public Genre() {
        id = 0;
        name = "";
        description = null;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<BookGenre> getBookGenreSet() {
        return bookGenreSet;
    }

    public void setBookGenreSet(Set<BookGenre> bookGenreSet) {
        this.bookGenreSet = bookGenreSet;
    }

    // toString
    @Override
    public String toString() {
        return "''" + id + ": " + name + " (" + description + ")";
    }
}
