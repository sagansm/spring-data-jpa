package me.ssagan.springdatajpa.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.AuthorCreateDto;
import me.ssagan.springdatajpa.dto.AuthorDto;
import me.ssagan.springdatajpa.dto.AuthorWithBooksDto;
import me.ssagan.springdatajpa.dto.BookWithGenreDto;
import me.ssagan.springdatajpa.entity.Author;
import me.ssagan.springdatajpa.repository.AuthorRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    final AuthorRepository repository;

    @Override
    public List<AuthorDto> getAllAuthors() {
        return repository.findAll().stream().map(this::convertToAuthorDto).toList();
    }

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

    @Override
    public AuthorDto createAuthor(AuthorCreateDto dto) {
        Author author = convertToEntity(dto);
        Author savedAuthor = repository.save(author);
        return convertToAuthorDto(savedAuthor);
    }

    @Override
    public AuthorDto updateAuthor(Long id, AuthorCreateDto dto) {
        Author author = repository.findById(id).orElseThrow();
        author.setName(dto.getName());
        author.setSurname(dto.getSurname());
        Author savedAuthor = repository.save(author);
        return convertToAuthorDto(savedAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        repository.deleteById(id);
    }

    private Author convertToEntity(AuthorCreateDto dto) {
        return Author.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .build();
    }

    private AuthorWithBooksDto convertToDto(Author author) {
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

    private AuthorDto convertToAuthorDto(Author author) {
        return AuthorDto
                .builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }
}
