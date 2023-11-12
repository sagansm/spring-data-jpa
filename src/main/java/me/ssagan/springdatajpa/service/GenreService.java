package me.ssagan.springdatajpa.service;

import me.ssagan.springdatajpa.dto.GenreCreateDto;
import me.ssagan.springdatajpa.dto.GenreDto;
import me.ssagan.springdatajpa.dto.GenreWithBooksDto;

public interface GenreService {
    GenreWithBooksDto getGenreById(Long id);

    GenreDto createGenre(GenreCreateDto dto);

    GenreDto getGenreByName(String name);
}
