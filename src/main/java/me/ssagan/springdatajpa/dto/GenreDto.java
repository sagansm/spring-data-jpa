package me.ssagan.springdatajpa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreDto {
    Long id;
    String name;
}
