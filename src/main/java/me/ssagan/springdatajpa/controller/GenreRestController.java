package me.ssagan.springdatajpa.controller;

import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.GenreCreateDto;
import me.ssagan.springdatajpa.dto.GenreDto;
import me.ssagan.springdatajpa.dto.GenreWithBooksDto;
import me.ssagan.springdatajpa.service.GenreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GenreRestController {
    final GenreService service;

    @GetMapping("genre/{id}")
    GenreWithBooksDto getGenreById(@PathVariable("id") Long id) {
        return service.getGenreById(id);
    }

    @PostMapping("/genre")
    GenreDto createGenre(@RequestBody GenreCreateDto genreCreateDto) {
        return service.createGenre(genreCreateDto);
    }

}
