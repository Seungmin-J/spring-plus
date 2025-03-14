package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.response.QSearchTodoResponse;
import org.example.expert.domain.todo.dto.response.SearchTodoResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class CustomTodoRepositoryImpl implements CustomTodoRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomTodoRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        QTodo todo = QTodo.todo;
        QUser user = QUser.user;

        Todo result = jpaQueryFactory.selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<SearchTodoResponse> searchTodos(
            String title,
            String nickname,
            String startDate,
            String endDate,
            Pageable pageable)
    {
        QTodo todo = QTodo.todo;
        QUser user = QUser.user;
        QComment comment = QComment.comment;
        QManager manager = QManager.manager;

        List<SearchTodoResponse> result = jpaQueryFactory.select(new QSearchTodoResponse(
                        todo.title,
                        manager.countDistinct(),
                        comment.countDistinct()
                ))
                .from(todo)
                .leftJoin(todo.user, user)
                .leftJoin(todo.comments, comment)
                .leftJoin(todo.managers, manager)
                .where(
                        titleContains(title),
                        nicknameContains(nickname),
                        createdAtBetween(startDate, endDate)
                )
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(todo.count())
                .from(todo)
                .where(
                        titleContains(title),
                        nicknameContains(nickname),
                        createdAtBetween(startDate, endDate)
                );

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? QTodo.todo.title.contains(title) : null;
    }

    private BooleanExpression nicknameContains(String nickname) {
        return nickname != null ? QTodo.todo.user.nickname.contains(nickname) : null;
    }

    private BooleanExpression createdAtBetween(String startDate, String endDate) {
        QTodo todo = QTodo.todo;

        if (startDate == null && endDate == null) {
            return null;
        } else if (startDate == null) {
            return todo.createdAt.loe(LocalDate.parse(endDate).atStartOfDay());
        } else if (endDate == null) {
            return todo.createdAt.goe(LocalDate.parse(startDate).atStartOfDay()); //
        } else {
            return todo.createdAt.between(LocalDate.parse(startDate).atStartOfDay(), LocalDate.parse(endDate).atTime(LocalTime.MAX));
        }
    }

}
