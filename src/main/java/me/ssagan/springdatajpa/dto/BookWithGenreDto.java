package me.ssagan.springdatajpa.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookWithGenreDto {
    Long id;
    String name;
    String genre;
}
