package me.ssagan.springdatajpa.repository;

import me.ssagan.springdatajpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Optional<Book> findBookByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM BOOK WHERE name = ?")
    Optional<Book> findBookByNameBySql(String name);
}
