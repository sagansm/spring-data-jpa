package me.ssagan.springdatajpa.dto.mapper;

import me.ssagan.springdatajpa.dto.GenreWithBooksDto;
import me.ssagan.springdatajpa.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreWithBooksDtoMapper {
    GenreWithBooksDto toDto(Genre genre);
}
