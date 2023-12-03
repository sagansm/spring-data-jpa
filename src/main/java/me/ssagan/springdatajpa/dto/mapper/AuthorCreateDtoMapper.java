package me.ssagan.springdatajpa.dto.mapper;

import me.ssagan.springdatajpa.dto.AuthorCreateDto;
import me.ssagan.springdatajpa.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorCreateDtoMapper {
    Author toEntity(AuthorCreateDto dto);
}
