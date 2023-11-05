package me.ssagan.springdatajpa.controller;

import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.GenreDto;
import me.ssagan.springdatajpa.dto.GenreWithBooksDto;
import me.ssagan.springdatajpa.service.GenreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreController {
    final GenreService service;

    @GetMapping("/{id}")
    GenreWithBooksDto getGenreById(@PathVariable("id") Long id){
        return service.getGenreById(id);
    }
}
