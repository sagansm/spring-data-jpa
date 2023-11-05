package me.ssagan.springdatajpa.service;

import me.ssagan.springdatajpa.dto.AuthorWithBooksDto;
import me.ssagan.springdatajpa.dto.BookWithGenreDto;

public interface AuthorService {
    AuthorWithBooksDto getAuthorById(Long id);

    AuthorWithBooksDto getAuthorByNameV1(String name);

    AuthorWithBooksDto getAuthorByNameV2(String name);

    AuthorWithBooksDto getAuthorByNameV3(String name);
}
