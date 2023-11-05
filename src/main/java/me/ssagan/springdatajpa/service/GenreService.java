package me.ssagan.springdatajpa.service;

import me.ssagan.springdatajpa.dto.GenreDto;
import me.ssagan.springdatajpa.dto.GenreWithBooksDto;

public interface GenreService {
    GenreWithBooksDto getGenreById(Long id);
}
