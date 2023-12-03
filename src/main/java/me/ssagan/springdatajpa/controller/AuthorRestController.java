package me.ssagan.springdatajpa.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.AuthorCreateDto;
import me.ssagan.springdatajpa.dto.AuthorDto;
import me.ssagan.springdatajpa.dto.AuthorWithBooksDto;
import me.ssagan.springdatajpa.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "library-users")
public class AuthorRestController {
    final AuthorService service;

    @GetMapping("/author/{id}")
    AuthorWithBooksDto getAuthorById(@PathVariable("id") Long id) {
        return service.getAuthorById(id);
    }

    @GetMapping("/author/v1")
    AuthorWithBooksDto getAuthorByName(@RequestParam("name") String name) {
        return service.getAuthorByName(name);
    }

    @GetMapping("/author/v2")
    AuthorWithBooksDto getAuthorByNameBySql(@RequestParam("name") String name) {
        return service.getAuthorByNameBySql(name);
    }

    @GetMapping("/author/v3")
    AuthorWithBooksDto getAuthorByNameByCriteria(@RequestParam("name") String name) {
        return service.getAuthorByNameByCriteria(name);
    }

    @PostMapping("/author")
    AuthorDto createAuthor(@RequestBody @Valid AuthorCreateDto authorCreateDto) {
        return service.createAuthor(authorCreateDto);
    }

    @PutMapping("/author/{id}")
    AuthorDto updateAuthor(@PathVariable("id") Long id, @RequestBody @Valid AuthorCreateDto authorCreateDto) {
        return service.updateAuthor(id, authorCreateDto);
    }

    @DeleteMapping("/author/{id}")
    void deleteAuthor(@PathVariable("id") Long id) {
        service.deleteAuthor(id);
    }
}
