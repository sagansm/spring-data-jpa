package me.ssagan.springdatajpa.service;

import me.ssagan.springdatajpa.dto.AuthorCreateDto;
import me.ssagan.springdatajpa.dto.AuthorDto;
import me.ssagan.springdatajpa.dto.AuthorWithBooksDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();
    AuthorWithBooksDto getAuthorById(Long id);

    AuthorWithBooksDto getAuthorByNameV1(String name);

    AuthorWithBooksDto getAuthorByNameV2(String name);

    AuthorWithBooksDto getAuthorByNameV3(String name);

    AuthorDto createAuthor(AuthorCreateDto dto);

    AuthorDto updateAuthor(Long id, AuthorCreateDto dto);

    void deleteAuthor(Long id);
}
