package me.ssagan.springdatajpa.dto.mapper;

import me.ssagan.springdatajpa.dto.BookDto;
import me.ssagan.springdatajpa.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookDtoMapper {
    BookDto toDto(Book book);
}
