package me.ssagan.springdatajpa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {
    Long id;
    String name;
}
