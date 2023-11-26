package me.ssagan.springdatajpa.controller;

import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.*;
import me.ssagan.springdatajpa.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookController {
    final BookService service;

/*    @GetMapping("/books")
    String getBooksView(Model model) {
        model.addAttribute("books", service.gelAllBooks());
        return "books";
    }*/

    @GetMapping("book/{id}")
    BookWithGenreAndAuthorsDto getBookById(@PathVariable("id") Long id) {
        return service.getBookById(id);
    }

    @GetMapping("/book")
    BookWithGenreDto getBookByNameV1(@RequestParam("name") String name) {
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
