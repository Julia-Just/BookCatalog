package org.top.authorscatalog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.top.authorscatalog.entity.Book;
import org.top.authorscatalog.entity.Review;
import org.top.authorscatalog.service.BookService;
import org.top.authorscatalog.service.ReviewService;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("review")
public class ReviewController {

    private final BookService bookService;
    private final ReviewService reviewService;

    public ReviewController(BookService bookService, ReviewService reviewService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    @GetMapping("add")
    public String getAddForm(Model model) {
        Review review = new Review();
        Iterable<Book> books = bookService.findAll();
        model.addAttribute("review", review);
        model.addAttribute("books", books);
        return "review/add-review-form";
    }

    @GetMapping("add/{id}")
    public String getAddForm(@PathVariable Integer id, Model model) {
        Review review = new Review();
        Optional<Book> books = bookService.findById(id);
        model.addAttribute("review", review);
        model.addAttribute("books", new Book[]{books.get()});
        return "review/add-review-form";
    }

    @PostMapping("add")
    public String postAddForm(Review review) {
        reviewService.save(review);
        return "redirect:/book/" + review.getBook().getId();
    }

    @GetMapping("delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        Optional<Review> deleted = reviewService.deleteById(id);
        // TODO: обработка ошибки
        return deleted
                .map(review -> "redirect:/book/" + review.getBook().getId())
                .orElse("redirect:/book");
    }
}
