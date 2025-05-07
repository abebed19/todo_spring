package com.abayllc.todoapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.abayllc.todoapp.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Page<Todo> findByCompleted(boolean completed, Pageable pageable);
    Page<Todo> findByCompletedAndTitleContainingIgnoreCase(boolean completed, String title, Pageable pageable);
    Page<Todo> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
