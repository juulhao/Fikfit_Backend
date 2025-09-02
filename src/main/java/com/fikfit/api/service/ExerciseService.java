package com.fikfit.api.service;

import com.fikfit.api.entity.ExerciseEntity;
import com.fikfit.api.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<ExerciseEntity> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public Optional<ExerciseEntity> getExerciseById(Long id) {
        return exerciseRepository.findById(id);
    }

    public ExerciseEntity createExercise(ExerciseEntity exercise) {
        return exerciseRepository.save(exercise);
    }

    public ExerciseEntity updateExercise(Long id, ExerciseEntity exerciseData) {
        ExerciseEntity existingExercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found with id: " + id));
        
        existingExercise.setName(exerciseData.getName());
        existingExercise.setMuscleGroup(exerciseData.getMuscleGroup());
        existingExercise.setEquipment(exerciseData.getEquipment());
        existingExercise.setInstructions(exerciseData.getInstructions());
        
        return exerciseRepository.save(existingExercise);
    }

    public void deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new IllegalArgumentException("Exercise not found with id: " + id);
        }
        exerciseRepository.deleteById(id);
    }

    public List<ExerciseEntity> getExercisesByMuscleGroup(String muscleGroup) {
        return exerciseRepository.findByMuscleGroupIgnoreCase(muscleGroup);
    }

    public List<ExerciseEntity> searchExercisesByName(String name) {
        return exerciseRepository.findByNameContainingIgnoreCase(name);
    }
}