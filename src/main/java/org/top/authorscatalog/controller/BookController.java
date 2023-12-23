package org.top.authorscatalog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.authorscatalog.entity.Book;
import org.top.authorscatalog.entity.BookAuthor;
import org.top.authorscatalog.entity.BookGenre;
import org.top.authorscatalog.entity.Publisher;
import org.top.authorscatalog.form.BookFilterForm;
import org.top.authorscatalog.service.AuthorService;
import org.top.authorscatalog.service.BookService;
import org.top.authorscatalog.service.GenreService;
import org.top.authorscatalog.service.PublisherService;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

// BookController - контроллер для работы с книгами
@Controller
@RequestMapping("book")
public class BookController {

    // сервис для работы с книгами
    private final BookService bookService;
    private final PublisherService publisherService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookController(BookService bookService, PublisherService publisherService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.publisherService = publisherService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    // Обработчик вывода всех книг
    // http://localhost:8080/book
    @GetMapping("")
    public String findAll(BookFilterForm filter, Model model) {
        if (filter.isFormEmpty()) {
            Iterable<Book> books = bookService.findAll();
            model.addAttribute("books", books);
        } else {
            // сделать фильтрацию
            Iterable<Book> filteredBooks = bookService.filter(filter);
            model.addAttribute("books", filteredBooks);
        }
        model.addAttribute("filter", filter);
        return "book/book-list";
    }

    // Обработчик добавления книги (получает форму)
    // http://localhost:8080/book/new
    @GetMapping("new")
    public String getAddForm(Model model) {
        Book book = new Book();     // объект для заполнения в форме
        List<Publisher> publishers = (List<Publisher>) publisherService.findAll();
        model.addAttribute("publishers", publishers);
        model.addAttribute("book", book);
        return "book/add-book-form";
    }

    // Обработчик добавления книги (обрабатывает форму)
    // http://localhost:8080/book/new
    @PostMapping("new")
    public String postAddForm(Book book, @RequestParam MultipartFile previewImage, RedirectAttributes redirectAttributes) throws Exception {
        try {
            // перед добавлением надо декодировать данные из формы и записать в данные объекта
            String previewImageData = Base64.getEncoder().encodeToString(previewImage.getBytes());
            book.setPreviewImageData(previewImageData);
            Optional<Book> saved = bookService.save(book);
            if (saved.isPresent()) {
                redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                        "Книга " + saved.get() + " успешно добавлена");
            }
            return "redirect:/book";
        }
        catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause() instanceof SQLIntegrityConstraintViolationException) {
                System.out.println(">Возникла ошибка " + ex.getMessage());
                redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                        "Ошибка записи в БД: такой ISBN уже существует");
            }
            else {
                throw ex;
            }
        }
        return "redirect:/book/new";
    }

    // Обработчик удаления книги
    // http://localhost:8080/book/delete/{id}
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Book> deleted = bookService.deleteById(id);
        if (deleted.isPresent()) {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                    "Книга " + deleted.get() + " успешно удалена");
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Книга с id " + id + " не найдена");
        }
        return "redirect:/book";
    }

    // Обработчики редактирования книги (возвращает форму)
    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Book> updated = bookService.findById(id);
        List<Publisher> publishers = (List<Publisher>) publisherService.findAll();
        if (updated.isPresent()) {
            model.addAttribute("book", updated.get());
            model.addAttribute("publishers", publishers);
            return "book/update-book-form";
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Книга с id " + id + " не найдена");
            return "redirect:/update-book-form";
        }
    }

    // Обработчик добавления книги (обрабатывает форму)
    @PostMapping("/update")
    public String postUpdateForm(Book book, @RequestParam MultipartFile previewImage, RedirectAttributes redirectAttributes) throws Exception {
        try {
            String previewImageData = Base64.getEncoder().encodeToString(previewImage.getBytes());
            book.setPreviewImageData(previewImageData);
            Optional<Book> updated = bookService.update(book);
            if (updated.isPresent()) {
                redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                        "Запись книги " + updated.get() + " успешно обновлена");
            } else {
                redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                        "Не получилось выполнить обновление записи");
            }
        } catch (Exception ex) {
            throw ex;
        }
        return "redirect:/book";
    }

    // Вывод подробной информации о книге
    @GetMapping("{id}")
    public String details(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            model.addAttribute("bookAuthor", new BookAuthor());
            model.addAttribute("bookGenre", new BookGenre());
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "book/book-details";
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Книга с id " + id + " не найдена");
        }
        return "redirect:/book";
    }

    @PostMapping("{id}/add-author")
    public String addAuthor(@PathVariable Integer id, BookAuthor bookAuthor, RedirectAttributes redirectAttributes) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            bookAuthor.setBook(book.get());
            if (bookService.addAuthor(bookAuthor)) {
                redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                        "Автор добавлен");
            } else {
                redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                        "Автор не добавлен");
            }
            return "redirect:/book/" + bookAuthor.getBook().getId();
        } else {
            return "redirect:/book/0";    // не найден
        }
    }

    @PostMapping("{id}/add-genre")
    public String addGenre(@PathVariable Integer id, BookGenre bookGenre, RedirectAttributes redirectAttributes) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            bookGenre.setBook(book.get());
            if (bookService.addGenre(bookGenre)) {
                redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                        "Жанр добавлен");
            } else {
                redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                        "Жанр не добавлен");
            }
            return "redirect:/book/" + bookGenre.getBook().getId();
        } else {
            return "redirect:/book/0";    // не найден
        }
    }
}
