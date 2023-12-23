package org.top.authorscatalog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.authorscatalog.entity.Genre;
import org.top.authorscatalog.service.GenreService;

import java.util.Optional;

// GenreController - контроллер для работы с жанрами
@Controller
@RequestMapping("genre")
public class GenreController {

    // сервис для работы с жанрами
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    // Обработчик вывода всех жанра
    // http://localhost:8080/genre
    @GetMapping("")
    public String getAll(Model model) {
        Iterable<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "genre/genre-list";
    }

    // Обработчик добавления жанра (получает форму)
    // http://localhost:8080/genre/new
    @GetMapping("new")
    public String getAddForm(Model model) {
        Genre genre = new Genre();     // объект для заполнения в форме
        model.addAttribute("genre", genre);
        return "genre/add-genre-form";
    }

    // Обработчик добавления жанра (обрабатывает форму)
    // http://localhost:8080/genre/new
    @PostMapping("new")
    public String postAddForm(Genre genre, RedirectAttributes redirectAttributes) {
        Optional<Genre> saved = genreService.save(genre);
        if (saved.isPresent()) {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                    "Жанр " + saved.get() + " успешно добавлен");
        }
        // перенаправление запроса
        return "redirect:/genre";
    }

    // Обработчик удаления жанра
    // http://localhost:8080/genre/delete/{id}
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Genre> deleted = genreService.findById(id);
        if (deleted.isPresent()) {
            if (deleted.get().getBookGenreSet().isEmpty()) {
                genreService.deleteById(id);
                redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                        "Жанр " + deleted.get() + " успешно удален");
            } else {
                redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                        "Жанр с id " + id + " связан с книгами и его нельзя удалить");
            }
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Жанр с id " + id + " связан с книгами и его нельзя удалить");
        }
        return "redirect:/genre";
    }

    // Обработчики редактирования жанра (возвращает форму)
    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Genre> updated = genreService.findById(id);
        if (updated.isPresent()) {
            model.addAttribute("genre", updated.get());
            return "genre/update-genre-form";
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Жанр с id " + id + " не найден");
            return "redirect:/genre";
        }
    }

    // Обработчик добавления жанра (обрабатывает форму)
    @PostMapping("/update")
    public String postUpdateForm(Genre genre, RedirectAttributes redirectAttributes) {
        Optional<Genre> updated = genreService.update(genre);
        if (updated.isPresent()) {
            // записать сообщение, что успешно обновлен
            redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                    "Жанр " + updated.get() + " успешно обновлен");
        } else {
            // записать сообщение, что не получилось обновить
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Не получилось выполнить обновление"
            );
        }
        return "redirect:/genre";
    }

    // Вывод подробной информации о жанре
    @GetMapping("{id}")
    public String details(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Genre> genre = genreService.findById(id);
        if (genre.isPresent()) {
            model.addAttribute("genre", genre.get());
            return "genre/genre-details";
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Жанр с id " + id + " не найден");
            return "redirect:/genre";
        }
    }
}
