package me.ssagan.springdatajpa.controller;

import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.BookDto;
import me.ssagan.springdatajpa.dto.BookWithGenreDto;
import me.ssagan.springdatajpa.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookController {
    final BookService service;
    /*@GetMapping("/{id}")
    BookDto getBookById(@PathVariable("id") Long id){
        return service.getBookById(id);
    }*/

    @GetMapping("/book")
    BookWithGenreDto getBookByNameV1(@RequestParam("name") String name){
        return service.getBookByNameV1(name);
    }

    @GetMapping("/book/v2")
    BookWithGenreDto getBookByNameV2(@RequestParam("name") String name) {
        return service.getByNameV2(name);
    }

    @GetMapping("/book/v3")
    BookWithGenreDto getBookByNameV3(@RequestParam("name") String name) {
        return service.getByNameV3(name);
    }
}
