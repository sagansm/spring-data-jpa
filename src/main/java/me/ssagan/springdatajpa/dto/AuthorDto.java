package me.ssagan.springdatajpa.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDto {
    Long id;
    String name;
    String surname;
}
