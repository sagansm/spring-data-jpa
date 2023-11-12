package me.ssagan.springdatajpa.service;

import me.ssagan.springdatajpa.dto.*;

public interface BookService {
    BookWithGenreAndAuthorsDto getBookById(Long id);

    BookWithGenreDto getBookByNameV1(String name);

    BookWithGenreDto getByNameV2(String name);

    BookWithGenreDto getByNameV3(String name);

    BookDto createBook(BookCreateDto dto);

    BookDto updateBook(Long id, BookCreateDto dto);

    void deleteBook(Long id);

    BookDto createBookByStrings(BookCreateByStringsDto dto);

}
