package me.ssagan.springdatajpa.controller;

import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.*;
import me.ssagan.springdatajpa.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookRestController {
    final BookService service;

    @GetMapping("book/{id}")
    BookWithGenreAndAuthorsDto getBookById(@PathVariable("id") Long id) {
        return service.getBookById(id);
    }

    @GetMapping("/book")
    BookWithGenreDto getBookByNameV1(@RequestParam("name") String name) {
        return service.getBookByName(name);
    }

    @GetMapping("/book/v2")
    BookWithGenreDto getBookByNameBySql(@RequestParam("name") String name) {
        return service.getBookByNameBySql(name);
    }

    @GetMapping("/book/v3")
    BookWithGenreDto getBookByNameByCriteria(@RequestParam("name") String name) {
        return service.getByNameByCriteria(name);
    }

    @PostMapping("/book")
    BookDto createBook(@RequestBody BookCreateDto bookCreateDto) {
        return service.createBook(bookCreateDto);
    }

    @PostMapping("/bookbystrings")
    BookDto createBookByStrings(@RequestBody BookCreateByStringsDto dto) {
        return service.createBookByStrings(dto);
    }

    @PutMapping("/book/{id}")
    BookDto updateBook(@PathVariable("id") Long id, @RequestBody BookCreateDto bookCreateDto) {
        return service.updateBook(id, bookCreateDto);
    }

    @DeleteMapping("/book/{id}")
    void deleteBook(@PathVariable("id") Long id) {
        service.deleteBook(id);
    }
}
