package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.SearchTodoResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomTodoRepository {
    Optional<Todo> findByIdWithUser(Long todoId);

    Page<SearchTodoResponse> searchTodos(String title, String nickname, String startDate, String endDate, Pageable pageable);
}
