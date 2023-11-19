package me.ssagan.springdatajpa.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.*;
import me.ssagan.springdatajpa.entity.Author;
import me.ssagan.springdatajpa.entity.Book;
import me.ssagan.springdatajpa.entity.Genre;
import me.ssagan.springdatajpa.repository.AuthorRepository;
import me.ssagan.springdatajpa.repository.BookRepository;
import me.ssagan.springdatajpa.repository.GenreRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    final BookRepository repository;
    final GenreRepository genreRepository;
    final AuthorRepository authorRepository;

    @Override
    public List<BookWithGenreAndAuthorsDto> gelAllBooks() {
        List<Book> bookList= repository.findAll();
        return bookList.stream().map(this::convertToBookWithGenreAndAuthorsDtoDto).toList();
    }

    @Override
    public BookWithGenreAndAuthorsDto getBookById(Long id) {
        Book book = repository.findById(id).orElseThrow();
        return convertToBookWithGenreAndAuthorsDtoDto(book);
    }

    @Override
    public BookWithGenreDto getBookByNameV1(String name) {
        Book book = repository.findBookByName(name).orElseThrow();
        return convertToDto(book);
    }

    @Override
    public BookWithGenreDto getByNameV2(String name) {
        Book book = repository.findBookByNameBySql(name).orElseThrow();
        return convertToDto(book);
    }

    @Override
    public BookWithGenreDto getByNameV3(String name) {
        Specification<Book> specification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });
        Book book = repository.findOne(specification).orElseThrow();
        return convertToDto(book);
    }

    @Override
    public BookDto createBook(BookCreateDto dto) {
        Book book = convertToEntity(dto);
        Book savedBook = repository.save(book);
        return convertToBookDto(savedBook);
    }

    @Override
    public BookDto updateBook(Long id, BookCreateDto dto) {
        Book book = repository.findById(id).orElseThrow();
        book.setName(dto.getName());
        book.setGenre(Genre.builder().id(dto.getGenre_id()).build());
        book.setAuthors(dto.getAuthors().stream().map(author -> Author
                .builder()
                .id(author.getId())
                .build()).collect(Collectors.toSet()));
        Book savedBook = repository.save(book);
        return convertToBookDto(savedBook);
    }

    @Override
    public void deleteBook(Long id) {
        repository.deleteById(id);
    }

    @Override
    public BookDto createBookByStrings(BookCreateByStringsDto dto) {
        Book book = convertToEntity(dto);

        Genre genre;
        Optional<Genre> genreOptional = genreRepository.findGenreByName(book.getGenre().getName());
        if (genreOptional.isPresent()) {
            genre = genreOptional.get();
        } else {
            genre = genreRepository.save(Genre
                    .builder()
                    .name(book.getGenre().getName())
                    .build());
        }
        book.setGenre(genre);

        Set<Author> authorSet = dto
                .getAuthors()
                .stream()
                .map(authorCreateDto -> {
                    Author author;
                    Optional<Author> authorOptional = authorRepository
                            .findAuthorByNameAndSurname(authorCreateDto.getName(), authorCreateDto.getSurname());
                    if (authorOptional.isPresent()) {
                        author = authorOptional.get();
                    } else {
                        author = authorRepository.save(Author
                                .builder()
                                .name(authorCreateDto.getName())
                                .surname(authorCreateDto.getSurname())
                                .build());
                    }
                    return author;
                })
                .collect(Collectors.toSet());
        book.setAuthors(authorSet);
        Book savedBook = repository.save(book);
        return convertToBookDto(savedBook);
    }

    private Book convertToEntity(BookCreateDto dto) {
        Set<Author> authorSet = dto.getAuthors()
                .stream()
                .map(author -> Author
                        .builder()
                        .id(author.getId())
                        .build())
                .collect(Collectors.toSet());
        Genre genre = Genre
                .builder()
                .id(dto.getGenre_id())
                .build();
        return Book
                .builder()
                .name(dto.getName())
                .genre(genre)
                .authors(authorSet)
                .build();
    }

    private Book convertToEntity(BookCreateByStringsDto dto) {
        Set<Author> authorSet = dto.getAuthors()
                .stream()
                .map(author -> Author
                        .builder()
                        .name(author.getName())
                        .surname(author.getSurname())
                        .build())
                .collect(Collectors.toSet());
        Genre genre = Genre
                .builder()
                .name(dto.getGenre().getName())
                .build();
        return Book
                .builder()
                .name(dto.getName())
                .genre(genre)
                .authors(authorSet)
                .build();
    }

    BookWithGenreDto convertToDto(Book book) {
        return BookWithGenreDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .build();
    }

    BookDto convertToBookDto(Book book) {
        return BookDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .build();
    }

    BookWithGenreAndAuthorsDto convertToBookWithGenreAndAuthorsDtoDto(Book book) {
        List<AuthorDto> authorDtoList = book
                .getAuthors()
                .stream()
                .map(author -> AuthorDto
                        .builder()
                        .id(author.getId())
                        .name(author.getName())
                        .surname(author.getSurname())
                        .build())
                .toList();
        return BookWithGenreAndAuthorsDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .authors(authorDtoList)
                .build();
    }
}
