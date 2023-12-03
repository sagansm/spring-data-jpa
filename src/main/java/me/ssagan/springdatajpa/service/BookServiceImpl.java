package me.ssagan.springdatajpa.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.SpringDataJpaApplication;
import me.ssagan.springdatajpa.dto.*;
import me.ssagan.springdatajpa.dto.mapper.*;
import me.ssagan.springdatajpa.entity.Author;
import me.ssagan.springdatajpa.entity.Book;
import me.ssagan.springdatajpa.entity.Genre;
import me.ssagan.springdatajpa.repository.AuthorRepository;
import me.ssagan.springdatajpa.repository.BookRepository;
import me.ssagan.springdatajpa.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    final BookRepository repository;
    final GenreRepository genreRepository;
    final AuthorRepository authorRepository;
    final BookDtoMapper bookDtoMapper;
    final BookWithGenreDtoMapper bookWithGenreDtoMapper;
    final BookWithGenreAndAuthorsDtoMapper bookWithGenreAndAuthorsDtoMapper;
    final BookCreateDtoMapper bookCreateDtoMapper;
    final BookCreateByStringsDtoMapper bookCreateByStringsDtoMapper;
    static final Logger log = LoggerFactory.getLogger(SpringDataJpaApplication.class);

    @Override
    public List<BookWithGenreAndAuthorsDto> gelAllBooks() {
        log.info("Getting all books");
        List<Book> bookList = repository.findAll();
        return bookList.stream().map(bookWithGenreAndAuthorsDtoMapper::toDto).toList();
    }

    @Override
    public BookWithGenreAndAuthorsDto getBookById(Long id) {
        log.info("Try to find book by id {}", id);
        Optional<Book> bookOptional = repository.findById(id);
        if (bookOptional.isPresent()) {
            BookWithGenreAndAuthorsDto dto = bookWithGenreAndAuthorsDtoMapper.toDto(bookOptional.get());
            log.info("Book: {}", dto.toString());
            return dto;
        } else {
            log.error("Book with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookWithGenreDto getBookByName(String name) {
        log.info("Try to find book by name {}", name);
        Optional<Book> bookOptional = repository.findBookByName(name);
        if (bookOptional.isPresent()) {
            BookWithGenreDto dto = bookWithGenreDtoMapper.toDto(bookOptional.get());
            log.info("Book: {}", dto.toString());
            return dto;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookWithGenreDto getBookByNameBySql(String name) {
        log.info("Try to find book by name {}", name);
        Optional<Book> bookOptional = repository.findBookByNameBySql(name);
        if (bookOptional.isPresent()) {
            BookWithGenreDto dto = bookWithGenreDtoMapper.toDto(bookOptional.get());
            log.info("Book: {}", dto.toString());
            return dto;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookWithGenreDto getByNameByCriteria(String name) {
        log.info("Try to find book by name {}", name);
        Specification<Book> specification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });
        Optional<Book> bookOptional = repository.findOne(specification);
        if (bookOptional.isPresent()) {
            BookWithGenreDto dto = bookWithGenreDtoMapper.toDto(bookOptional.get());
            log.info("Book: {}", dto.toString());
            return dto;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDto createBook(BookCreateDto dto) {
        log.info("Try to create book: {}", dto.toString());
        Book book = bookCreateDtoMapper.toEntity(dto);
        try {
            Book savedBook = repository.save(book);
            BookDto saved_dto = bookDtoMapper.toDto(savedBook);
            log.info("Book saved: {}", saved_dto.toString());
            return saved_dto;
        } catch (RuntimeException e) {
            log.error("Book not saved: " + e.toString());
            throw new RuntimeException(e.toString());
        }
    }

    @Override
    public BookDto updateBook(Long id, BookCreateDto dto) {
        log.info("Try to update book id: {}, values: {}", id, dto.toString());
        Optional<Book> bookOptional = repository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setName(dto.getName());
            book.setGenre(Genre.builder().id(dto.getGenre_id()).build());
            book.setAuthors(dto.getAuthors().stream().map(author -> Author
                    .builder()
                    .id(author.getId())
                    .build()).collect(Collectors.toSet()));
            try {
                Book savedBook = repository.save(book);
                BookDto saved_dto = bookDtoMapper.toDto(savedBook);
                log.info("Book updated: {}", saved_dto.toString());
                return saved_dto;
            } catch (RuntimeException e) {
                log.error("Author not saved: " + e.toString());
                throw new RuntimeException(e.toString());
            }
        } else {
            log.error("Book with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Try to delete book id: {}", id);
        Optional<Book> bookOptional = repository.findById(id);
        if (bookOptional.isPresent()) {
            try {
                repository.deleteById(id);
                log.info("Book deleted id: {}", id);
            } catch (RuntimeException e) {
                log.error("Book not deleted: " + e.toString());
                throw new RuntimeException(e.toString());
            }
        } else {
            log.error("Book with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDto createBookByStrings(BookCreateByStringsDto dto) {
        log.info("Try to create book: {}", dto.toString());

        Book book = bookCreateByStringsDtoMapper.toEntity(dto);

        Genre genre;
        Optional<Genre> genreOptional = genreRepository.findGenreByName(book.getGenre().getName());
        if (genreOptional.isPresent()) {
            genre = genreOptional.get();
        } else {
            log.info("Try to create genre: {}", dto.getGenre().toString());
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
                        log.info("Try to create author: {}", authorCreateDto.toString());
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
        try {
            Book savedBook = repository.save(book);
            BookDto saved_dto = bookDtoMapper.toDto(savedBook);
            return saved_dto;
        } catch (RuntimeException e) {
            log.error("Book not saved: " + e.toString());
            throw new RuntimeException(e.toString());
        }
    }

    /*private Book convertToEntity(BookCreateDto dto) {
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
    }*/

    /*private Book convertToEntity(BookCreateByStringsDto dto) {
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
    }*/

    /*BookWithGenreDto convertToDto(Book book) {
        return BookWithGenreDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .build();
    }*/

    /*BookDto convertToBookDto(Book book) {
        return BookDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .build();
    }*/

    /*BookWithGenreAndAuthorsDto convertToBookWithGenreAndAuthorsDtoDto(Book book) {
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
    }*/
}
