package me.ssagan.springdatajpa.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    String name;
    GenreCreateDto genre;
    List<AuthorCreateDto> authors;
}
