package me.ssagan.springdatajpa.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.BookWithGenreDto;
import me.ssagan.springdatajpa.dto.GenreCreateDto;
import me.ssagan.springdatajpa.dto.GenreDto;
import me.ssagan.springdatajpa.dto.GenreWithBooksDto;
import me.ssagan.springdatajpa.service.GenreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "library-users")
public class GenreRestController {
    final GenreService service;

    @GetMapping("genre/{id}")
    GenreWithBooksDto getGenreById(@PathVariable("id") Long id) {
        return service.getGenreById(id);
    }

    @GetMapping("/genre")
    GenreDto getGenreByName(@RequestParam("name") String name) {
        return service.getGenreByName(name);
    }

    @PostMapping("/genre")
    GenreDto createGenre(@RequestBody GenreCreateDto genreCreateDto) {
        return service.createGenre(genreCreateDto);
    }
}
