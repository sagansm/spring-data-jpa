package me.ssagan.springdatajpa.controller;

import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BookController {
    final BookService service;

    @GetMapping("/books")
    String getBooksView(Model model) {
        model.addAttribute("books", service.gelAllBooks());
        return "books";
    }
}
