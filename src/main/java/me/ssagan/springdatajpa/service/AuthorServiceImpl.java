package me.ssagan.springdatajpa.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.AuthorWithBooksDto;
import me.ssagan.springdatajpa.dto.BookDto;
import me.ssagan.springdatajpa.dto.BookWithGenreDto;
import me.ssagan.springdatajpa.entity.Author;
import me.ssagan.springdatajpa.entity.Book;
import me.ssagan.springdatajpa.repository.AuthorRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService{

    final AuthorRepository repository;

    @Override
    public AuthorWithBooksDto getAuthorById(Long id) {
        Author author = repository.findById(id).orElseThrow();
        return convertToDto(author);
    }

    @Override
    public AuthorWithBooksDto getAuthorByNameV1(String name) {
        Author author = repository.findAuthorByName(name).orElseThrow();
        return convertToDto(author);
    }

    @Override
    public AuthorWithBooksDto getAuthorByNameV2(String name) {
        Author author = repository.findAuthorByNameBySql(name).orElseThrow();
        return convertToDto(author);
    }

    @Override
    public AuthorWithBooksDto getAuthorByNameV3(String name) {
        Specification<Author> specification = Specification.where(new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });

        Author author = repository.findOne(specification).orElseThrow();
        return convertToDto(author);
    }

    private AuthorWithBooksDto convertToDto(Author author){
        List<BookWithGenreDto> bookDtoList = author.getBooks()
                .stream()
                .map(book -> BookWithGenreDto
                        .builder()
                        .id(book.getId())
                        .name(book.getName())
                        .genre(book.getGenre().getName())
                        .build())
                .toList();
        return AuthorWithBooksDto
                .builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
    }
}
