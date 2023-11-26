package me.ssagan.springdatajpa.repository;

import me.ssagan.springdatajpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
