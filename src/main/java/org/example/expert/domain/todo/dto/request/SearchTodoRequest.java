package org.example.expert.domain.todo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchTodoRequest {

    private String title;
    private String nickname;
    private String startDate;
    private String endDate;
}
