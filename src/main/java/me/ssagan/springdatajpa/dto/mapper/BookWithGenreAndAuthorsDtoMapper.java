package me.ssagan.springdatajpa.dto.mapper;

import me.ssagan.springdatajpa.dto.AuthorDto;
import me.ssagan.springdatajpa.dto.BookWithGenreAndAuthorsDto;
import me.ssagan.springdatajpa.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookWithGenreAndAuthorsDtoMapper {
    default BookWithGenreAndAuthorsDto toDto(Book book){
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
        return BookWithGenreAndAuthorsDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .authors(authorDtoList)
                .build();
    };
}
