package me.ssagan.springdatajpa.controller;

import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthorController {
    final AuthorService service;

    @GetMapping("/authors")
    String getBooksView(Model model) {
        model.addAttribute("authors", service.getAllAuthors());
        return "authors";
    }
}
