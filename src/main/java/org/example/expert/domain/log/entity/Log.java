package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "log")
@Getter
@NoArgsConstructor
public class Log {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timeRequested;

    private boolean result;

    private String action;

    public Log(LocalDateTime timeRequested, boolean result, String action) {
        this.timeRequested = timeRequested;
        this.result = result;
        this.action = action;
    }
}
