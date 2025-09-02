package com.fikfit.api.repository;

import com.fikfit.api.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {
    List<ExerciseEntity> findByMuscleGroupIgnoreCase(String muscleGroup);
    List<ExerciseEntity> findByNameContainingIgnoreCase(String name);
}