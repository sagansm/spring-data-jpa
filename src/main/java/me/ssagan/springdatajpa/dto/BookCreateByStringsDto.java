package me.ssagan.springdatajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookCreateByStringsDto {
    String name;
    GenreCreateDto genre;
    List<AuthorCreateDto> authors;
}
