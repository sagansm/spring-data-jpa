package me.ssagan.springdatajpa.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookCreateDto {
    String name;
    Long genre_id;
    List<AuthorIdDto> authors;
}