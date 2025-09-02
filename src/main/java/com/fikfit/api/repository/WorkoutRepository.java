package com.fikfit.api.repository;

import com.fikfit.api.entity.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<WorkoutEntity, Long> {
    List<WorkoutEntity> findByStudentId(Long studentId);
    
    @Query("SELECT w FROM WorkoutEntity w LEFT JOIN FETCH w.exercises we LEFT JOIN FETCH we.exercise WHERE w.id = :id")
    WorkoutEntity findByIdWithExercises(@Param("id") Long id);
    
    @Query("SELECT w FROM WorkoutEntity w LEFT JOIN FETCH w.exercises we LEFT JOIN FETCH we.exercise WHERE w.student.id = :studentId")
    List<WorkoutEntity> findByStudentIdWithExercises(@Param("studentId") Long studentId);
}