package com.fikfit.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "workout_exercises")
@Entity
@Getter
@Setter
public class WorkoutExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private WorkoutEntity workout;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private ExerciseEntity exercise;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Column(nullable = false)
    private Integer sets;

    @Column(nullable = false, length = 20)
    private String reps; // "12" ou "12-15" ou "até falha"

    @Column(length = 20)
    private String weight; // "40kg" ou "livre"

    @Column(nullable = false, length = 20)
    private String rest; // "60s" ou "1min"

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}