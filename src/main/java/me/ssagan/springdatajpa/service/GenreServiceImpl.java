package me.ssagan.springdatajpa.service;

import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.SpringDataJpaApplication;
import me.ssagan.springdatajpa.dto.*;
import me.ssagan.springdatajpa.dto.mapper.GenreCreateDtoMapper;
import me.ssagan.springdatajpa.dto.mapper.GenreDtoMapper;
import me.ssagan.springdatajpa.dto.mapper.GenreWithBooksDtoMapper;
import me.ssagan.springdatajpa.entity.Author;
import me.ssagan.springdatajpa.entity.Book;
import me.ssagan.springdatajpa.entity.Genre;
import me.ssagan.springdatajpa.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    final GenreRepository repository;
    final GenreDtoMapper genreDtoMapper;
    final GenreWithBooksDtoMapper genreWithBooksDtoMapper;
    final GenreCreateDtoMapper genreCreateDtoMapper;
    static final Logger log = LoggerFactory.getLogger(SpringDataJpaApplication.class);

    @Override
    public GenreWithBooksDto getGenreById(Long id) {
        log.info("Try to find genre by id {}", id);
        Optional<Genre> genreOptional = repository.findById(id);
        if (genreOptional.isPresent()) {
            GenreWithBooksDto dto = genreWithBooksDtoMapper.toDto(genreOptional.get());
            log.info("Genre: {}", dto.toString());
            return dto;
        } else {
            log.error("Genre with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public GenreDto createGenre(GenreCreateDto dto) {
        log.info("Try to create genre: {}", dto.toString());
        Genre genre = genreCreateDtoMapper.toEntity(dto);
        try {
            Genre savedGenre = repository.save(genre);
            GenreDto saved_dto = genreDtoMapper.toDto(savedGenre);
            log.info("Genre saved: {}", saved_dto.toString());
            return saved_dto;
        } catch (RuntimeException e) {
            log.error("Genre not saved: " + e.toString());
            throw new RuntimeException(e.toString());
        }
    }

    @Override
    public GenreDto getGenreByName(String name) {
        log.info("Try to find genre by name {}", name);
        Optional<Genre> genreOptional = repository.findGenreByName(name);
        if(genreOptional.isPresent()){
        GenreDto dto = genreDtoMapper.toDto(genreOptional.get());
            log.info("Genre: {}", dto.toString());
        return dto;
        } else {
            log.error("Genre with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    /*private Genre convertToEntity(GenreCreateDto dto) {
        return Genre
                .builder()
                .name(dto.getName())
                .build();
    }*/

    /*GenreWithBooksDto convertToDto(Genre genre) {
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
    }*/

    /*GenreDto convertToGenreDto(Genre genre) {
        return GenreDto
                .builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }*/
}
