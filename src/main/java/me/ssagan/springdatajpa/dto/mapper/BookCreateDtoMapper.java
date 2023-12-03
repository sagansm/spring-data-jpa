package me.ssagan.springdatajpa.dto.mapper;

import me.ssagan.springdatajpa.dto.BookCreateDto;
import me.ssagan.springdatajpa.entity.Author;
import me.ssagan.springdatajpa.entity.Book;
import me.ssagan.springdatajpa.entity.Genre;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookCreateDtoMapper {
    default Book toEntity(BookCreateDto dto){
        Set<Author> authorSet = dto.getAuthors()
                .stream()
                .map(author -> Author
                        .builder()
                        .id(author.getId())
                        .build())
                .collect(Collectors.toSet());
        Genre genre = Genre
                .builder()
                .id(dto.getGenre_id())
                .build();
        return Book
                .builder()
                .name(dto.getName())
                .genre(genre)
                .authors(authorSet)
                .build();
    }
}
