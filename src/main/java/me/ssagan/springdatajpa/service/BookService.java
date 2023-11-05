package me.ssagan.springdatajpa.service;

import me.ssagan.springdatajpa.dto.BookDto;
import me.ssagan.springdatajpa.dto.BookWithGenreDto;

public interface BookService {
    BookWithGenreDto getBookById(Long id);
    BookWithGenreDto getBookByNameV1(String name);

    public BookWithGenreDto getByNameV2(String name);

    public BookWithGenreDto getByNameV3(String name);

}
