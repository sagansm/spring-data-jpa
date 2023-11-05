package me.ssagan.springdatajpa.controller;

import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.AuthorWithBooksDto;
import me.ssagan.springdatajpa.dto.BookWithGenreDto;
import me.ssagan.springdatajpa.service.AuthorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    final AuthorService service;

    @GetMapping("/author/{id}")
    AuthorWithBooksDto getAuthorById(@PathVariable ("id") Long id){
        return service.getAuthorById(id);
    }

    @GetMapping("/author/v1")
    AuthorWithBooksDto getAuthorByNameV1(@RequestParam("name") String name){
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

}
