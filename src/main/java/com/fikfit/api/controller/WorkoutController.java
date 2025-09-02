package com.fikfit.api.controller;

import com.fikfit.api.entity.WorkoutEntity;
import com.fikfit.api.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workouts")
@CrossOrigin(origins = "*")
public class WorkoutController {
    
    @Autowired
    private WorkoutService workoutService;
    
    @GetMapping
    public ResponseEntity<List<WorkoutEntity>> getAllWorkouts() {
        try {
            List<WorkoutEntity> workouts = workoutService.getAllWorkouts();
            return ResponseEntity.ok(workouts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutEntity> getWorkoutById(@PathVariable Long id) {
        try {
            Optional<WorkoutEntity> workout = workoutService.getWorkoutById(id);
            return workout.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}/with-exercises")
    public ResponseEntity<WorkoutEntity> getWorkoutByIdWithExercises(@PathVariable Long id) {
        try {
            WorkoutEntity workout = workoutService.getWorkoutByIdWithExercises(id);
            if (workout != null) {
                return ResponseEntity.ok(workout);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<WorkoutEntity>> getWorkoutsByStudentId(@PathVariable Long studentId) {
        try {
            List<WorkoutEntity> workouts = workoutService.getWorkoutsByStudentId(studentId);
            return ResponseEntity.ok(workouts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/student/{studentId}/with-exercises")
    public ResponseEntity<List<WorkoutEntity>> getWorkoutsByStudentIdWithExercises(@PathVariable Long studentId) {
        try {
            List<WorkoutEntity> workouts = workoutService.getWorkoutsByStudentIdWithExercises(studentId);
            return ResponseEntity.ok(workouts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<WorkoutEntity> createWorkout(@RequestBody WorkoutEntity workout) {
        try {
            WorkoutEntity createdWorkout = workoutService.createWorkout(workout);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkout);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutEntity> updateWorkout(@PathVariable Long id, @RequestBody WorkoutEntity workoutDetails) {
        try {
            WorkoutEntity updatedWorkout = workoutService.updateWorkout(id, workoutDetails);
            return ResponseEntity.ok(updatedWorkout);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        try {
            workoutService.deleteWorkout(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}