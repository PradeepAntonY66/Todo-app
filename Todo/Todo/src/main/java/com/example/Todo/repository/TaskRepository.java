package com.example.Todo.repository;

import com.example.Todo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByName(String task);
    boolean existsByName(String name);
}
