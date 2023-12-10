package me.ssagan.springdatajpa.service;


import me.ssagan.springdatajpa.dto.*;
import me.ssagan.springdatajpa.dto.mapper.*;
import me.ssagan.springdatajpa.entity.Author;
import me.ssagan.springdatajpa.entity.Book;
import me.ssagan.springdatajpa.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.postgresql.hostchooser.HostRequirement.any;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorService")
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorDtoMapper authorDtoMapper;

    @Mock
    private AuthorWithBooksDtoMapper authorWithBooksDtoMapper;
    @Mock
    private AuthorCreateDtoMapper authorCreateDtoMapper;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    @DisplayName(" get auathor by id should return AuthorWithBooksDto")
    public void testGetAuthorById() {
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorWithBooksDtoMapper.toDto(author)).thenReturn(AuthorWithBooksDto
                .builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build());

        AuthorWithBooksDto authorDto = authorService.getAuthorById(id);

        verify(authorRepository).findById(id);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    @DisplayName("should throw NoSuchElementException")
    public void testGetAuthorByIdNotFound() {
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getAuthorById(id));

        verify(authorRepository).findById(id);
    }

    @Test
    @DisplayName("get author by name should return AuthorWithBooksDto")
    public void testGetAuthorByName() {
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);
        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));
        when(authorWithBooksDtoMapper.toDto(author)).thenReturn(AuthorWithBooksDto
                .builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build());

        AuthorWithBooksDto authorDto = authorService.getAuthorByName(name);

        verify(authorRepository).findAuthorByName(name);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    @DisplayName("create author should return AuthorDto")
    public void testCreateAuthor() {
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);
        when(authorRepository.save(author)).thenReturn(author);
        when(authorDtoMapper.toDto(author)).thenReturn(AuthorDto
                .builder()
                .id(id)
                .name(name)
                .surname(surname)
                .build());
        when(authorCreateDtoMapper.toEntity(any())).thenReturn(author);

        AuthorDto authorDto = authorService.createAuthor(
                AuthorCreateDto
                        .builder()
                        .name(name)
                        .surname(surname)
                        .build());

        verify(authorRepository).save(author);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

}
