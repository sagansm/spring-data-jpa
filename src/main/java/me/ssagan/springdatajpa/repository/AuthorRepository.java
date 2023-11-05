package me.ssagan.springdatajpa.repository;

import me.ssagan.springdatajpa.entity.Author;
import me.ssagan.springdatajpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    Optional<Author> findAuthorByName(String name);

    @Query(nativeQuery = true, value = "select * from author where name = ?")
    Optional<Author> findAuthorByNameBySql(String name);
}
