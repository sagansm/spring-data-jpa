package me.ssagan.springdatajpa.dto.mapper;

import me.ssagan.springdatajpa.dto.BookCreateByStringsDto;
import me.ssagan.springdatajpa.entity.Author;
import me.ssagan.springdatajpa.entity.Book;
import me.ssagan.springdatajpa.entity.Genre;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookCreateByStringsDtoMapper {
    default Book toEntity(BookCreateByStringsDto dto){
        Set<Author> authorSet = dto.getAuthors()
                .stream()
                .map(author -> Author
                        .builder()
                        .name(author.getName())
                        .surname(author.getSurname())
                        .build())
                .collect(Collectors.toSet());
        Genre genre = Genre
                .builder()
                .name(dto.getGenre().getName())
                .build();
        return Book
                .builder()
                .name(dto.getName())
                .genre(genre)
                .authors(authorSet)
                .build();
    };
}
