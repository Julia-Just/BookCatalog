package org.top.authorscatalog.entity;

import jakarta.persistence.*;

import java.util.Date;

// Review описывает сущность "Отзыв о книге" - запись в таблице отзывов
@Entity
@Table(name = "review_t")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "review_rate_f", nullable = false)
    private Integer reviewRate;           // оценка книги, поставленная автором книги

    @Column(name = "comment_f")
    private String comment;              // комментарий отзыва

    @Column(name = "written_in")
    public Date writtenIn;

    // Связь: отзыв ссылается на книгу, много отзывов к одной книге

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

     // конструктор

    public Review() {
        id = 0;
        reviewRate = 0;
        comment = null;
        writtenIn = null;
    }

    // геттеры и сеттеры

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReviewRate() {
        return reviewRate;
    }

    public void setReviewRate(Integer reviewRate) {
        this.reviewRate = reviewRate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getWrittenIn() {
        return writtenIn;
    }

    public void setWrittenIn(Date writtenIn) {
        this.writtenIn = writtenIn;
    }
    // toString

    @Override
    public String toString() {
        return "Отзыв: " + "id=" + id + ", оценка: " + reviewRate +", комментарий: " + comment;
    }
}
