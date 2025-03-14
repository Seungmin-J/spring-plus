package org.example.expert.domain.todo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class SearchTodoResponse {

    private final String title;

    private final Long numOfManagers;

    private final Long numOfComments;

    @QueryProjection
    public SearchTodoResponse(String title, Long numOfManagers, Long numOfComments) {
        this.title = title;
        this.numOfManagers = numOfManagers;
        this.numOfComments = numOfComments;
    }
}
