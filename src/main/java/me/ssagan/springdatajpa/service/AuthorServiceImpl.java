package me.ssagan.springdatajpa.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.SpringDataJpaApplication;
import me.ssagan.springdatajpa.dto.AuthorCreateDto;
import me.ssagan.springdatajpa.dto.AuthorDto;
import me.ssagan.springdatajpa.dto.AuthorWithBooksDto;
import me.ssagan.springdatajpa.dto.BookWithGenreDto;
import me.ssagan.springdatajpa.dto.mapper.AuthorCreateDtoMapper;
import me.ssagan.springdatajpa.dto.mapper.AuthorDtoMapper;
import me.ssagan.springdatajpa.dto.mapper.AuthorWithBooksDtoMapper;
import me.ssagan.springdatajpa.entity.Author;
import me.ssagan.springdatajpa.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    private final AuthorCreateDtoMapper authorCreateDtoMapper;

    private final AuthorDtoMapper authorDtoMapper;

    private final AuthorWithBooksDtoMapper authorWithBooksDtoMapper;

    static final Logger log = LoggerFactory.getLogger(SpringDataJpaApplication.class);

    @Override
    public List<AuthorDto> getAllAuthors() {
        log.info("Getting all authors");
        return repository.findAll().stream().map(authorDtoMapper::toDto).toList();
        //return repository.findAll().stream().map(this::convertToAuthorDto).toList();
    }

    @Override
    public AuthorWithBooksDto getAuthorById(Long id) {
        log.info("Try to find author by id {}", id);
        Optional<Author> authorOptional = repository.findById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            AuthorWithBooksDto my_dto = authorWithBooksDtoMapper.toDto(author);
            //AuthorWithBooksDto dto = convertToDto(authorOptional.get());
            log.info("Author: {}", my_dto.toString());
            return my_dto;
        } else {
            log.error("Author with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorWithBooksDto getAuthorByName(String name) {
        log.info("Try to find author by name {}", name);
        Optional<Author> authorOptional = repository.findAuthorByName(name);
        if(authorOptional.isPresent()){
            AuthorWithBooksDto dto = authorWithBooksDtoMapper.toDto(authorOptional.get());
            //AuthorWithBooksDto dto = convertToDto(authorOptional.get());
            log.info("Author: {}", dto.toString());
            return dto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorWithBooksDto getAuthorByNameBySql(String name) {
        log.info("Try to find author by name {}", name);
        Optional<Author> authorOptional = repository.findAuthorByNameBySql(name);
        if(authorOptional.isPresent()){
            AuthorWithBooksDto dto = authorWithBooksDtoMapper.toDto(authorOptional.get());
            //AuthorWithBooksDto dto = convertToDto(authorOptional.get());
            log.info("Author: {}", dto.toString());
            return dto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorWithBooksDto getAuthorByNameByCriteria(String name) {
        log.info("Try to find author by name {}", name);
        Specification<Author> specification = Specification.where(new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });

        Optional<Author> authorOptional = repository.findOne(specification);
        if(authorOptional.isPresent()){
            AuthorWithBooksDto dto = authorWithBooksDtoMapper.toDto(authorOptional.get());
            //AuthorWithBooksDto dto = convertToDto(authorOptional.get());
            log.info("Author: {}", dto.toString());
            return dto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto dto) {
        log.info("Try to create author: {}", dto.toString());
        Author author = authorCreateDtoMapper.toEntity(dto);
        try {
            Author savedAuthor = repository.save(author);
            AuthorDto saved_dto = authorDtoMapper.toDto(savedAuthor);
            //AuthorDto saved_dto = convertToAuthorDto(savedAuthor);
            log.info("Author saved: {}", saved_dto.toString());
            return saved_dto;
        } catch (RuntimeException e) {
            log.error("Author not saved: " + e.toString());
            throw new RuntimeException(e.toString());
        }
    }

    @Override
    public AuthorDto updateAuthor(Long id, AuthorCreateDto dto) {
        log.info("Try to update author id: {}, values: {}", id, dto.toString());
        Optional<Author> authorOptional = repository.findById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            author.setName(dto.getName());
            author.setSurname(dto.getSurname());
            try {
                Author savedAuthor = repository.save(author);
                AuthorDto saved_dto = authorDtoMapper.toDto(savedAuthor);
                //AuthorDto saved_dto = convertToAuthorDto(savedAuthor);
                log.info("Author updated: {}", saved_dto.toString());
                return authorDtoMapper.toDto(savedAuthor);
                //return convertToAuthorDto(savedAuthor);
            } catch (RuntimeException e) {
                log.error("Author not saved: " + e.toString());
                throw new RuntimeException(e.toString());
            }
        } else {
            log.error("Author with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Try to delete author id: {}", id);
        Optional<Author> authorOptional = repository.findById(id);
        if (authorOptional.isPresent()) {
            try {
                repository.deleteById(id);
                log.info("Author deleted id: {}", id);
            } catch (RuntimeException e) {
                log.error("Author not deleted: " + e.toString());
                throw new RuntimeException(e.toString());
            }
        } else {
            log.error("Author with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    /*private Author convertToEntity(AuthorCreateDto dto) {
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
    }*/
}
