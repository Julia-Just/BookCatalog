package org.top.authorscatalog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.top.authorscatalog.entity.Client;
import org.top.authorscatalog.service.ClientService;

import java.util.Optional;

// ClientController - контроллер для работы с клиентами
@Controller
@RequestMapping("client")
public class ClientController {

    // сервис для работы с клиентами
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Обработчик вывода всех клиентов
    // http://localhost:8080/client
    @GetMapping("")
    public String getAll(Model model) {
        Iterable<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "client/client-list";
    }

    // Обработчик добавления клиента (получает форму)
    // http://localhost:8080/client/new
    @GetMapping("new")
    public String getAddForm(Model model) {
        Client client = new Client();   // объект для заполнения в форме
        model.addAttribute("client", client);
        return "client/add-client-form";
    }

    // Обработчик добавления объекта (обрабатывает форму)
    // http://localhost:8080/client/new
    @PostMapping("new")
    public String postAddForm(Client client, RedirectAttributes redirectAttributes) {
        Optional<Client> saved = clientService.save(client);
        if (saved.isPresent()) {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                    "Клиент " + saved.get() + " успешно добавлен");
        }
        return "redirect:/client";
    }

    // Обработчик удаления объекта
    // http://localhost:8080/client/delete/{id}
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Client> deleted = clientService.deleteById(id);
        if (deleted.isPresent()) {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                    "Клиент " + deleted.get() + " успешно удален");
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Клиент с id " + id + " не найден");
        }
        return "redirect:/client";
    }

    // Обработчики редактирования объекта (возвращает форму)
    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Client> updated = clientService.findById(id);
        if (updated.isPresent()) {
            model.addAttribute("client", updated.get());
            return "client/update-client-form";
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Клиент с id " + id + " не найден");
            return "redirect:/client";
        }
    }

    // Обработчик добавления объекта (обрабатывает форму)
    @PostMapping("/update")
    public String postUpdateForm(Client client, RedirectAttributes redirectAttributes) {
        Optional<Client> updated = clientService.update(client);
        if (updated.isPresent()) {
            // записать сообщение, что успешно обновлен
            redirectAttributes.addFlashAttribute(ViewMessageKeys.SUCCESS_MESSAGE,
                    "\"Клиент " + updated.get() + " успешно обновлен");
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Не получилось выполнить обновление"
            );
        }
        return "redirect:/client";
    }

    // Вывод подробной информации о жанре
    @GetMapping("{id}")
    public String details(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            model.addAttribute("client", client.get());
            return "client/client-details";
        } else {
            redirectAttributes.addFlashAttribute(ViewMessageKeys.DANGER_MESSAGE,
                    "Клиент с id " + id + " не найден");
        }
        return "redirect:/client";
    }
}
