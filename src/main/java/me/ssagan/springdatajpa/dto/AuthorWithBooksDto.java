package me.ssagan.springdatajpa.dto;

import lombok.Builder;
import lombok.Data;

import java.awt.print.Book;
import java.util.List;

@Data
@Builder
public class AuthorWithBooksDto {
    Long id;
    String name;
    String surname;
    List<BookWithGenreDto> books;
}
