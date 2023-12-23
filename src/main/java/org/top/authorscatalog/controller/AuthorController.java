package org.top.authorscatalog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.authorscatalog.entity.Author;
import org.top.authorscatalog.service.AuthorService;

import java.util.Optional;

// AuthorController - контроллер для работы с авторами
@Controller
@RequestMapping("author")
public class AuthorController {

    // сервис для работы с авторами
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Обработчик вывода всех авторов
    // http://localhost:8080/author
    @GetMapping("")
    public String getAll(Model model) {
        Iterable<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "author/author-list";
    }

    // Обработчик добавления объекта (получает форму)
    // http://localhost:8080/author/new
    @GetMapping("new")
    public String getAddForm(Model model) {
        Author author = new Author();     // объект для заполнения в форме
        model.addAttribute("author", author);
        return "author/add-author-form";
    }

    // Обработчик добавления объекта (обрабатывает форму)
    @PostMapping("new")
    public String postAddForm(Author author, RedirectAttributes redirectAttributes) {
        Optional<Author> saved = authorService.save(author, false);
        if (saved.isPresent()) {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                    "Автор " + saved.get() + " успешно добавлен");
        } else {
            redirectAttributes.addFlashAttribute(
                    "author",
                    author);
            return "author/add-author-force-form";
        }
        // перенаправление запроса
        return "redirect:/author";
    }

    // Обработчик редактирования объекта
    @PostMapping("new/force")
    public String postAddFormForce(Author author, RedirectAttributes redirectAttributes) {
        Optional<Author> saved = authorService.save(author, true);
        if (saved.isPresent()) {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                    "Автор " + saved.get() + " успешно добавлен");
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Автор не добавлен");
        }
        // перенаправление запроса
        return "redirect:/author";
    }

    // Обработчик удаления объекта
    // http://localhost:8080/hotel/delete/{id}
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Author> deleted = authorService.findById(id);
        if (deleted.isPresent()) {
            if (deleted.get().getBookAuthorSet().isEmpty()) {
                redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                        "Автор " + deleted.get() + " успешно удален");
            } else {
                redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                        "Автор с id " + id + " связан с книгами и его нельзя удалить");
            }
        } else {
            // написать сообщение, что не найден
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Автор с id " + id + " не найден");
        }
        return "redirect:/author";
    }

    // Обработчик редактирования объекта (возвращает форму)
    // http://localhost:8080/hotel/update/{id}
    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Author> updated = authorService.findById(id);
        if (updated.isPresent()) {
            // добавить объект в модель
            model.addAttribute("author", updated.get());
            return "author/update-author-form";
        } else {
            // написать сообщение, что не найден
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Автор с id " + id + " не найден");
        }
        return "redirect:/author";
    }

    // Обработчик редактирования объекта (обрабатывает форму)
    @PostMapping("/update")
    public String postUpdateForm(Author author, RedirectAttributes redirectAttributes) {
        Optional<Author> updated = authorService.update(author);
        if (updated.isPresent()) {
            // написать сообщение, что успешно обновлён
            redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                    "Автор " + updated.get() + " успешно изменен");
        } else {
            // написать сообщение, что не получилось обновить
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Не получилось выполнить обновление");
        }
        return "redirect:/author";
    }

    // Вывод подробной информации от одном авторе
    @GetMapping("{id}")
    public String details(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Author> author = authorService.findById(id);
        if (author.isPresent()) {
            model.addAttribute("author", author.get());
            return "author/author-details";
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Автор с id " + id + " не найден");
            return "redirect:/author";
        }
    }
}
