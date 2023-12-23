package org.top.authorscatalog.entity;

import jakarta.persistence.*;

import java.util.Set;

// класс описывает сущность "Книга" - запись таблицы книг
@Entity
@Table(name = "book_t")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title_f", nullable = false)
    private String title;             // Название книги

    @Column(name = "isbn_f", nullable = false, unique = true)
    private String isbn;             // ISBN книги

    @Column(name = "description_f")
    private String description;     // Описание книги

    @Column(name = "price_f", nullable = false)
    private Double price;           // Цена книги

    @Column(name = "is_available_f", nullable = false)
    private Boolean available;    // Наличие книги на складе

    @Lob
    @Column(name="preview_image_f", columnDefinition = "MEDIUMBLOB")
    private String previewImageData; // строка хранит байты изображения

    // Связи

    @OneToMany(mappedBy = "book")

    private Set<Review> reviewSet;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @OneToMany(mappedBy = "book")
    private Set<BookAuthor> bookAuthorSet;

    @OneToMany(mappedBy = "book")
    private Set<BookGenre> bookGenreSet;

    // конструкторы

    public Book() {
        id = 0;
        title = "";
        isbn = "";
        description = null;
        price = 0.0;
        available = false;
    }

    // сеттеры и геттеры

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Set<Review> getReviewSet() {
        return reviewSet;
    }

    public void setReviewSet(Set<Review> reviewSet) {
        this.reviewSet = reviewSet;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Set<BookAuthor> getBookAuthorSet() {
        return bookAuthorSet;
    }

    public void setBookAuthorSet(Set<BookAuthor> bookAuthorSet) {
        this.bookAuthorSet = bookAuthorSet;
    }

    public Set<BookGenre> getBookGenreSet() {
        return bookGenreSet;
    }

    public void setBookGenreSet(Set<BookGenre> bookGenreSet) {
        this.bookGenreSet = bookGenreSet;
    }

    public String getPreviewImageData() {
        return previewImageData;
    }

    public void setPreviewImageData(String previewImageData) {
        this.previewImageData = previewImageData;
    }

    // toString

    @Override
    public String toString() {
        return title + ", isbn: " + isbn;
    }
}
