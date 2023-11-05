package me.ssagan.springdatajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class GenreDto {
    Long id;
    String name;
}
