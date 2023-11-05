package me.ssagan.springdatajpa.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookWithAuthorsDto {
    Long id;
    String name;
    List<AuthorDto> authors;
}
