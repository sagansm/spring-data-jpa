package me.ssagan.springdatajpa.dto.mapper;

import me.ssagan.springdatajpa.dto.AuthorDto;
import me.ssagan.springdatajpa.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorDtoMapper {
    AuthorDto toDto(Author author);
}
