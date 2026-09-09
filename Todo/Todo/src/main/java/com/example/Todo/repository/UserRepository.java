package com.example.Todo.repository;

import com.example.Todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String name);
    boolean existsByEmail(String email);
}
