package com.fikfit.api.repository;

import com.fikfit.api.entity.WorkoutExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExerciseEntity, Long> {
    List<WorkoutExerciseEntity> findByWorkoutIdOrderByOrderIndexAsc(Long workoutId);
    
    void deleteByWorkoutId(Long workoutId);
}