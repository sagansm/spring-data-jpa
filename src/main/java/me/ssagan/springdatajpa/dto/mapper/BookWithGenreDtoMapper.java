package me.ssagan.springdatajpa.dto.mapper;

import me.ssagan.springdatajpa.dto.BookWithGenreDto;
import me.ssagan.springdatajpa.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookWithGenreDtoMapper {
    @Mapping(source = "genre.id", target = "genre")
    BookWithGenreDto toDto(Book book);
}
