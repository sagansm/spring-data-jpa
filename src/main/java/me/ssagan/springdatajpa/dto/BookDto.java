package me.ssagan.springdatajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class BookDto {
    Long id;
    String name;
}
