package me.ssagan.springdatajpa.service;

import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.AuthorDto;
import me.ssagan.springdatajpa.dto.BookWithAuthorsDto;
import me.ssagan.springdatajpa.dto.GenreWithBooksDto;
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
}
