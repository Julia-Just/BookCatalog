package org.top.authorscatalog.service;

import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.Book;
import org.top.authorscatalog.entity.BookAuthor;
import org.top.authorscatalog.entity.BookGenre;
import org.top.authorscatalog.form.BookFilterForm;

import java.util.Optional;

@Service
public interface BookService {

    // получить все книги
    Iterable<Book> findAll();

    // получить книгу по id
    Optional<Book> findById(Integer id);

    // добавить книгу
    Optional<Book> save(Book book) throws Exception;

    // удалить книгу
    Optional<Book> deleteById(Integer id);

    // обновить книгу
    Optional<Book> update(Book book) throws Exception;

    // метод для работы с жанрами
    boolean addGenre(BookGenre bookGenre);
    boolean addAuthor(BookAuthor bookAuthor);

    // метод получения всех товаров, соответствующих форме
    Iterable<Book> filter(BookFilterForm form);
}
