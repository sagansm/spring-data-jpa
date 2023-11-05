package me.ssagan.springdatajpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Genre {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long id;
    String name;
    @OneToMany(mappedBy = "genre")
    Set<Book> books;
}
