package com.dev.timetracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class TimeEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    private int hours;

    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
