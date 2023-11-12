package me.ssagan.springdatajpa.service;

import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.*;
import me.ssagan.springdatajpa.entity.Genre;
import me.ssagan.springdatajpa.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    final GenreRepository repository;

    @Override
    public GenreWithBooksDto getGenreById(Long id) {
        Genre genre = repository.findById(id).orElseThrow();
        return convertToDto(genre);
    }

    @Override
    public GenreDto createGenre(GenreCreateDto dto) {
        Genre genre = convertToEntity(dto);
        Genre savedGenre = repository.save(genre);
        return convertToGenreDto(savedGenre);
    }

    @Override
    public GenreDto getGenreByName(String name) {
        Genre genre = repository.findGenreByName(name).orElseThrow();
        return convertToGenreDto(genre);
    }

    private Genre convertToEntity(GenreCreateDto dto) {
        return Genre
                .builder()
                .name(dto.getName())
                .build();
    }

    GenreWithBooksDto convertToDto(Genre genre) {
        List<BookWithAuthorsDto> bookWithAuthorsDtoList = genre.getBooks()
                .stream()
                .map(book -> {
                            List<AuthorDto> authorDtoList = book
                                    .getAuthors()
                                    .stream()
                                    .map(author -> AuthorDto
                                            .builder()
                                            .id(author.getId())
                                            .name(author.getName())
                                            .surname(author.getSurname())
                                            .build())
                                    .toList();
                            return BookWithAuthorsDto
                                    .builder()
                                    .id(book.getId())
                                    .name(book.getName())
                                    .authors(authorDtoList)
                                    .build();
                        }
                )
                .toList();

        return GenreWithBooksDto
                .builder()
                .id(genre.getId())
                .name(genre.getName())
                .books(bookWithAuthorsDtoList)
                .build();
    }

    GenreDto convertToGenreDto(Genre genre) {
        return GenreDto
                .builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
