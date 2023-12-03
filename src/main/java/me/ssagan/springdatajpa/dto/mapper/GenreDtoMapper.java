package me.ssagan.springdatajpa.dto.mapper;

import me.ssagan.springdatajpa.dto.GenreDto;
import me.ssagan.springdatajpa.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreDtoMapper {
    GenreDto toDto(Genre genre);
}
