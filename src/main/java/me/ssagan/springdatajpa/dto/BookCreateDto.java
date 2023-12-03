package me.ssagan.springdatajpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookCreateDto {
    @NotBlank
    String name;
    Long genre_id;
    List<AuthorIdDto> authors;
}