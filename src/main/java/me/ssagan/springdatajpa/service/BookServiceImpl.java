package me.ssagan.springdatajpa.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import me.ssagan.springdatajpa.dto.BookWithGenreDto;
import me.ssagan.springdatajpa.entity.Book;
import me.ssagan.springdatajpa.repository.BookRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    final BookRepository repository;

    @Override
    public BookWithGenreDto getBookById(Long id) {
        Book book = repository.findById(id).orElseThrow();
        return convertToDto(book);
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

    BookWithGenreDto convertToDto(Book book) {
        return BookWithGenreDto
                .builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .build();
    }
}
