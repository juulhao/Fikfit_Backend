package com.fikfit.api.service;

import com.fikfit.api.entity.WorkoutEntity;
import com.fikfit.api.entity.WorkoutExerciseEntity;
import com.fikfit.api.repository.WorkoutRepository;
import com.fikfit.api.repository.WorkoutExerciseRepository;
import com.fikfit.api.repository.StudentRepository;
import com.fikfit.api.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {
    
    @Autowired
    private WorkoutRepository workoutRepository;
    
    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private ExerciseRepository exerciseRepository;
    
    public List<WorkoutEntity> getAllWorkouts() {
        return workoutRepository.findAll();
    }
    
    public Optional<WorkoutEntity> getWorkoutById(Long id) {
        return workoutRepository.findById(id);
    }
    
    public WorkoutEntity getWorkoutByIdWithExercises(Long id) {
        return workoutRepository.findByIdWithExercises(id);
    }
    
    public List<WorkoutEntity> getWorkoutsByStudentId(Long studentId) {
        return workoutRepository.findByStudentId(studentId);
    }
    
    public List<WorkoutEntity> getWorkoutsByStudentIdWithExercises(Long studentId) {
        return workoutRepository.findByStudentIdWithExercises(studentId);
    }
    
    @Transactional
    public WorkoutEntity createWorkout(WorkoutEntity workout) {
        // Verificar se o estudante existe
        if (workout.getStudent() != null && workout.getStudent().getId() != null) {
            studentRepository.findById(workout.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
        }
        
        WorkoutEntity savedWorkout = workoutRepository.save(workout);
        
        // Salvar exercícios da ficha se existirem
        if (workout.getExercises() != null && !workout.getExercises().isEmpty()) {
            for (WorkoutExerciseEntity exercise : workout.getExercises()) {
                exercise.setWorkout(savedWorkout);
                // Verificar se o exercício existe
                if (exercise.getExercise() != null && exercise.getExercise().getId() != null) {
                    exerciseRepository.findById(exercise.getExercise().getId())
                        .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));
                }
                workoutExerciseRepository.save(exercise);
            }
        }
        
        return savedWorkout;
    }
    
    @Transactional
    public WorkoutEntity updateWorkout(Long id, WorkoutEntity workoutDetails) {
        WorkoutEntity workout = workoutRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ficha de treino não encontrada"));
        
        workout.setName(workoutDetails.getName());
        workout.setDescription(workoutDetails.getDescription());
        
        // Atualizar estudante se fornecido
        if (workoutDetails.getStudent() != null && workoutDetails.getStudent().getId() != null) {
            studentRepository.findById(workoutDetails.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
            workout.setStudent(workoutDetails.getStudent());
        }
        
        WorkoutEntity updatedWorkout = workoutRepository.save(workout);
        
        // Atualizar exercícios se fornecidos
        if (workoutDetails.getExercises() != null) {
            // Remover exercícios existentes
            workoutExerciseRepository.deleteByWorkoutId(id);
            
            // Adicionar novos exercícios
            for (WorkoutExerciseEntity exercise : workoutDetails.getExercises()) {
                exercise.setWorkout(updatedWorkout);
                // Verificar se o exercício existe
                if (exercise.getExercise() != null && exercise.getExercise().getId() != null) {
                    exerciseRepository.findById(exercise.getExercise().getId())
                        .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));
                }
                workoutExerciseRepository.save(exercise);
            }
        }
        
        return updatedWorkout;
    }
    
    @Transactional
    public void deleteWorkout(Long id) {
        WorkoutEntity workout = workoutRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ficha de treino não encontrada"));
        
        // Remover exercícios da ficha primeiro
        workoutExerciseRepository.deleteByWorkoutId(id);
        
        // Remover a ficha
        workoutRepository.delete(workout);
    }
}