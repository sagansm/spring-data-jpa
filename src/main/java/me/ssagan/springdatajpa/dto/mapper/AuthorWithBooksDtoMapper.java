package me.ssagan.springdatajpa.dto.mapper;

import me.ssagan.springdatajpa.dto.AuthorWithBooksDto;
import me.ssagan.springdatajpa.dto.BookWithGenreDto;
import me.ssagan.springdatajpa.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorWithBooksDtoMapper {
    //@Mapping(source = "genre.name", target = "genre")
    default AuthorWithBooksDto toDto(Author author){
        List<BookWithGenreDto> bookDtoList = author.getBooks()
                .stream()
                .map(book -> BookWithGenreDto
                        .builder()
                        .id(book.getId())
                        .name(book.getName())
                        .genre(book.getGenre().getName())
                        .build())
                .toList();
        return AuthorWithBooksDto
                .builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
    }
}
