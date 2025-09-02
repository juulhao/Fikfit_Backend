package com.fikfit.api.controller;

import com.fikfit.api.entity.ExerciseEntity;
import com.fikfit.api.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseEntity>> getAllExercises(
            @RequestParam(required = false) String muscleGroup,
            @RequestParam(required = false) String search) {
        
        List<ExerciseEntity> exercises;
        
        if (muscleGroup != null && !muscleGroup.isEmpty()) {
            exercises = exerciseService.getExercisesByMuscleGroup(muscleGroup);
        } else if (search != null && !search.isEmpty()) {
            exercises = exerciseService.searchExercisesByName(search);
        } else {
            exercises = exerciseService.getAllExercises();
        }
        
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseEntity> getExerciseById(@PathVariable Long id) {
        Optional<ExerciseEntity> exercise = exerciseService.getExerciseById(id);
        return exercise.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ExerciseEntity> createExercise(@RequestBody ExerciseEntity exercise) {
        ExerciseEntity createdExercise = exerciseService.createExercise(exercise);
        return ResponseEntity.ok(createdExercise);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseEntity> updateExercise(@PathVariable Long id, @RequestBody ExerciseEntity exerciseData) {
        try {
            ExerciseEntity updatedExercise = exerciseService.updateExercise(id, exerciseData);
            return ResponseEntity.ok(updatedExercise);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        try {
            exerciseService.deleteExercise(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}