package me.ssagan.springdatajpa.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookWithGenreAndAuthorsDto {
    Long id;
    String name;
    List<AuthorDto> authors;
    String genre;
}
