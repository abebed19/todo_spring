package com.abayllc.todoapp.controller;

import com.abayllc.todoapp.model.Todo;
import com.abayllc.todoapp.repository.TodoRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    @GetMapping
    public ResponseEntity<Page<Todo>> getAll(@RequestParam(required = false) Boolean completed, @RequestParam(required = false) String title,  Pageable pageable) {
        Page<Todo> todos ;
        if(completed != null &&  title != null) {
            todos= todoRepository.findByCompletedAndTitleContainingIgnoreCase(completed, title, pageable);
        }else if(completed != null) {
            todos = todoRepository.findByCompleted(completed, pageable);
        }
        else if( title != null) {
                todos = todoRepository.findByTitleContainingIgnoreCase(title, pageable);
            }
        else {
            todos = todoRepository.findAll(pageable);
        }
       return ResponseEntity.ok(todos);
    }
    @PostMapping
    public Todo createTodo(@Valid @RequestBody Todo todo) {
        return todoRepository.save(todo);
    }
    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id){
        return todoRepository.findById(id).orElseThrow(()->new NoSuchElementException("Todo not found with id" +id));
    }
    @PutMapping
    public Todo updateTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        if(todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            ResponseEntity.noContent().build();
        }
        throw new NoSuchElementException("Todo not found with id" + id);
    }
}
