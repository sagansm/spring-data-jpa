package me.ssagan.springdatajpa.controller;

import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.AuthorCreateDto;
import me.ssagan.springdatajpa.dto.AuthorDto;
import me.ssagan.springdatajpa.dto.AuthorWithBooksDto;
import me.ssagan.springdatajpa.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthorController {
    final AuthorService service;

    @GetMapping("/authors")
    String getBooksView(Model model) {
        model.addAttribute("authors", service.getAllAuthors());
        return "authors";
    }

    /*@GetMapping("/author/{id}")
    AuthorWithBooksDto getAuthorById(@PathVariable("id") Long id) {
        return service.getAuthorById(id);
    }

    @GetMapping("/author/v1")
    AuthorWithBooksDto getAuthorByNameV1(@RequestParam("name") String name) {
        return service.getAuthorByNameV1(name);
    }

    @GetMapping("/author/v2")
    AuthorWithBooksDto getAuthorByNameV2(@RequestParam("name") String name) {
        return service.getAuthorByNameV2(name);
    }

    @GetMapping("/author/v3")
    AuthorWithBooksDto getAuthorByNameV3(@RequestParam("name") String name) {
        return service.getAuthorByNameV3(name);
    }

    @PostMapping("/author")
    AuthorDto createAuthor(@RequestBody AuthorCreateDto authorCreateDto) {
        return service.createAuthor(authorCreateDto);
    }

    @PutMapping("/author/{id}")
    AuthorDto updateAuthor(@PathVariable("id") Long id, @RequestBody AuthorCreateDto authorCreateDto) {
        return service.updateAuthor(id, authorCreateDto);
    }

    @DeleteMapping("/author/{id}")
    void deleteAuthor(@PathVariable("id") Long id) {
        service.deleteAuthor(id);
    }*/
}
