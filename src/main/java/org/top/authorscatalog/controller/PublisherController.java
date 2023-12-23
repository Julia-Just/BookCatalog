package org.top.authorscatalog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.authorscatalog.entity.Publisher;
import org.top.authorscatalog.service.BookService;
import org.top.authorscatalog.service.PublisherService;

import java.util.Optional;

// PublisherController - контроллер для работы с издательствами
@Controller
@RequestMapping("publisher")
public class PublisherController {

    // сервис для работы с издательствами
    private final PublisherService publisherService;
    private final BookService bookService;

    public PublisherController(PublisherService publisherService, BookService bookService) {
        this.publisherService = publisherService;
        this.bookService = bookService;
    }

    // Обработчик вывода всех жанров
    // http://localhost:8080/publisher
    @GetMapping("")
    public String getAll(Model model) {
        Iterable<Publisher> publishers = publisherService.findAll();
        model.addAttribute("publishers", publishers);
        return "publisher/publisher-list";
    }

    @GetMapping("new")
    public String getAddForm(Model model) {
        Publisher publisher = new Publisher();
        model.addAttribute("publisher", publisher);
        return "publisher/add-publisher-form";
    }

    // Обработчик добавления объекта (обрабатывает форму)
    // http://localhost:8080/publisher/new
    @PostMapping("new")
    public String postAddForm(Publisher publisher, RedirectAttributes redirectAttributes) {
        Optional<Publisher> saved = publisherService.save(publisher);
        if (saved.isPresent()) {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                    "Издательство " + saved.get() + " успешно добавлено");
        }
        // перенаправление запроса
        return "redirect:/publisher";
    }

    // Обработчик удаления объекта
    // http://localhost:8080/publisher/delete/{id}
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Publisher> deleted = publisherService.findById(id);
        if (deleted.isPresent()) {
            if (deleted.get().getBookSet().isEmpty()) {
                publisherService.deleteById(id);
                redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                        "Издательство " + deleted.get() + " успешно удалено");
            } else {
                redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                        "Издательство с id " + id + " связано с книгами и его нельзя удалить");
            }
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Издательство с id " + id + " не найдено");
        }
        return "redirect:/publisher";
    }

    // Обработчики редактирования объекта (возвращает форму)
    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Publisher> updated = publisherService.findById(id);
        if (updated.isPresent()) {
            model.addAttribute("publisher", updated.get());
            return "publisher/update-publisher-form";
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Издательство с id " + id + " не найдено");
            return "redirect:/publisher";
        }
    }

    // Обработчик добавления объекта (обрабатывает форму)
    @PostMapping("/update")
    public String postUpdateForm(Publisher publisher, RedirectAttributes redirectAttributes) {
        Optional<Publisher> updated = publisherService.update(publisher);
        if (updated.isPresent()) {
            // записать сообщение, что успешно обновлен
            redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                    "Издательство " + updated.get() + " успешно обновлено");
        } else {
            // записать сообщение, что не получилось обновить
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Не получилось выполнить обновление"
            );
        }
        return "redirect:/publisher";
    }

    // Вывод подробной информации об издательстве
    @GetMapping("{id}")
    public String details(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Publisher> publisher = publisherService.findById(id);
        if (publisher.isPresent()) {
            model.addAttribute("publisher", publisher.get());
            return "publisher/publisher-details";
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Издательство с id " + id + " не найдено");
            return "redirect:/publisher";
        }
    }
}