package org.top.authorscatalog.rdb;

import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.*;
import org.top.authorscatalog.form.BookFilterForm;
import org.top.authorscatalog.rdb.repository.BookAuthorRepository;
import org.top.authorscatalog.rdb.repository.BookGenreRepository;
import org.top.authorscatalog.rdb.repository.BookRepository;
import org.top.authorscatalog.service.AuthorService;
import org.top.authorscatalog.service.BookService;
import org.top.authorscatalog.service.GenreService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

// RdbBookService - имплементация BookService, работающая с СУБД
@Service
public class RdbBookService implements BookService {

    // репозиторий для выполнения операций
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookAuthorRepository bookAuthorRepository;
    private final BookGenreRepository bookGenreRepository;

    public RdbBookService(BookRepository bookRepository, AuthorService authorService, GenreService genreService, BookAuthorRepository bookAuthorRepository, BookGenreRepository bookGenreRepository) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookGenreRepository = bookGenreRepository;
    }

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> save(Book book) throws Exception {
        return Optional.of(bookRepository.save(book));
    }

    @Override
    public Optional<Book> deleteById(Integer id) {
        Optional<Book> deleted = findById(id);
        if (deleted.isPresent()) {
            bookRepository.deleteById(id);
        }
        return deleted;
    }

    @Override
    public Optional<Book> update(Book book) throws Exception {
        Optional<Book> updated = findById(book.getId());
        if (updated.isPresent()) {
            updated = Optional.of(bookRepository.save(book));
        }
        return updated;
    }

    @Override
    public boolean addAuthor(BookAuthor bookAuthor) {
        // 1. проверить, есть ли такой автор и книга
        Optional<Book> book = findById(bookAuthor.getBook().getId());
        if (book.isEmpty()) {
            return false;
        }
        Optional<Author> author = authorService.findById(bookAuthor.getAuthor().getId());
        if (author.isEmpty()) {
            return false;
        }
        // 2. проверить, нет ли уже такого автора в этой книге
        Integer newBookAuthorId = bookAuthor.getAuthor().getId();
        Set<BookAuthor> bookAuthors = book.get().getBookAuthorSet();
        for (BookAuthor ba : bookAuthors) {
            if (Objects.equals(ba.getAuthor().getId(), newBookAuthorId)) {
                return false;
            }
        }
        bookAuthorRepository.save(bookAuthor);
        return true;
    }

    @Override
    public Iterable<Book> filter(BookFilterForm form) {
        // Каскадная фильтрация:
        // Возьмем все продукты и начнем фильтравать их
        List<Book> books = (List<Book>)findAll();
        if (!form.getBook().equals("")) {
            // отфильтровать по названию и описанию: оставить только те продукты, которые в
            // title или description содержат подстроку, указанную в form.product
            String pattern = form.getBook().toLowerCase();
            books = books.stream()
                    .filter(p -> p.getTitle().toLowerCase().contains(pattern) ||
                            p.getDescription().toLowerCase().contains(pattern)
                    )
                    .toList();
        }
        if (form.getMinPrice() != 0) {
            books = books.stream()
                    .filter(p -> p.getPrice() >= form.getMinPrice())
                    .toList();
        }
        if (form.getMaxPrice() != 0) {
            books = books.stream()
                    .filter(p -> p.getPrice() <= form.getMaxPrice())
                    .toList();
        }
        return books;
    }

    @Override
    public boolean addGenre(BookGenre bookGenre) {
        // 1. проверить, есть ли такой жанр и книга
        Optional<Book> book = findById(bookGenre.getBook().getId());
        if (book.isEmpty()) {
            return false;
        }
        Optional<Genre> genre = genreService.findById(bookGenre.getGenre().getId());
        if (genre.isEmpty()) {
            return false;
        }
        // 2. проверить, нет ли уже такого автора в этой книге
        Integer newBookGenreId = bookGenre.getGenre().getId();
        Set<BookGenre> bookGenres = book.get().getBookGenreSet();
        for (BookGenre bg : bookGenres) {
            if (bg.getGenre().getId() == newBookGenreId) {
                return false;
            }
        }
        bookGenreRepository.save(bookGenre);
        return true;
    }
}
