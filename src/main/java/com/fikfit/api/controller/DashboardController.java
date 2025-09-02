package com.fikfit.api.controller;

import com.fikfit.api.entity.AppointmentEntity;
import com.fikfit.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private WorkoutService workoutService;
    
    @Autowired
    private ExerciseService exerciseService;
    
    @Autowired
    private MeasurementService measurementService;
    
    @Autowired
    private AppointmentService appointmentService;
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // Estatísticas gerais
            stats.put("totalStudents", studentService.getAllStudents().size());
            stats.put("totalWorkouts", workoutService.getAllWorkouts().size());
            stats.put("totalExercises", exerciseService.getAllExercises().size());
            stats.put("totalMeasurements", measurementService.getAllMeasurements().size());
            stats.put("totalAppointments", appointmentService.getAllAppointments().size());
            
            // Agendamentos por status
            Map<String, Long> appointmentsByStatus = new HashMap<>();
            for (AppointmentEntity.AppointmentStatus status : AppointmentEntity.AppointmentStatus.values()) {
                appointmentsByStatus.put(status.name(), 
                    (long) appointmentService.getAppointmentsByStatus(status).size());
            }
            stats.put("appointmentsByStatus", appointmentsByStatus);
            
            // Próximos agendamentos (próximos 7 dias)
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime nextWeek = now.plusDays(7);
            List<AppointmentEntity> upcomingAppointments = appointmentService.getAppointmentsByDateRange(now, nextWeek);
            stats.put("upcomingAppointmentsCount", upcomingAppointments.size());
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/recent-activities")
    public ResponseEntity<Map<String, Object>> getRecentActivities() {
        try {
            Map<String, Object> activities = new HashMap<>();
            
            // Últimos 5 estudantes cadastrados
            List<Map<String, Object>> recentStudents = studentService.getAllStudents().stream()
                .sorted((s1, s2) -> s2.getCreatedAt().compareTo(s1.getCreatedAt()))
                .limit(5)
                .map(student -> {
                    Map<String, Object> studentData = new HashMap<>();
                    studentData.put("id", student.getId());
                    studentData.put("name", student.getName());
                    studentData.put("email", student.getEmail());
                    studentData.put("createdAt", student.getCreatedAt());
                    return studentData;
                })
                .toList();
            activities.put("recentStudents", recentStudents);
            
            // Próximos agendamentos (próximos 5)
            List<Map<String, Object>> upcomingAppointments = appointmentService.getUpcomingAppointments().stream()
                .limit(5)
                .map(appointment -> {
                    Map<String, Object> appointmentData = new HashMap<>();
                    appointmentData.put("id", appointment.getId());
                    appointmentData.put("title", appointment.getTitle());
                    appointmentData.put("studentName", appointment.getStudent().getName());
                    appointmentData.put("appointmentDate", appointment.getAppointmentDate());
                    appointmentData.put("type", appointment.getType());
                    appointmentData.put("status", appointment.getStatus());
                    return appointmentData;
                })
                .toList();
            activities.put("upcomingAppointments", upcomingAppointments);
            
            // Últimas medidas registradas (últimas 5)
            List<Map<String, Object>> recentMeasurements = measurementService.getAllMeasurements().stream()
                .sorted((m1, m2) -> m2.getCreatedAt().compareTo(m1.getCreatedAt()))
                .limit(5)
                .map(measurement -> {
                    Map<String, Object> measurementData = new HashMap<>();
                    measurementData.put("id", measurement.getId());
                    measurementData.put("studentName", measurement.getStudent().getName());
                    measurementData.put("measurementDate", measurement.getMeasurementDate());
                    measurementData.put("weight", measurement.getWeight());
                    measurementData.put("createdAt", measurement.getCreatedAt());
                    return measurementData;
                })
                .toList();
            activities.put("recentMeasurements", recentMeasurements);
            
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/student/{studentId}/summary")
    public ResponseEntity<Map<String, Object>> getStudentSummary(@PathVariable Long studentId) {
        try {
            Map<String, Object> summary = new HashMap<>();
            
            // Informações do estudante
            var student = studentService.getStudentById(studentId);
            if (student == null) {
                return ResponseEntity.notFound().build();
            }
            
            summary.put("student", student);
            
            // Fichas de treino do estudante
            var workouts = workoutService.getWorkoutsByStudentId(studentId);
            summary.put("totalWorkouts", workouts.size());
            
            // Medidas do estudante
            var measurements = measurementService.getMeasurementsByStudentId(studentId);
            summary.put("totalMeasurements", measurements.size());
            
            // Última medida
            var latestMeasurement = measurementService.getLatestMeasurementByStudentId(studentId);
            summary.put("latestMeasurement", latestMeasurement.orElse(null));
            
            // Agendamentos do estudante
            var appointments = appointmentService.getAppointmentsByStudentId(studentId);
            summary.put("totalAppointments", appointments.size());
            
            // Próximos agendamentos do estudante
            var upcomingAppointments = appointmentService.getUpcomingAppointmentsByStudentId(studentId);
            summary.put("upcomingAppointments", upcomingAppointments);
            
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/appointments/today")
    public ResponseEntity<List<AppointmentEntity>> getTodayAppointments() {
        try {
            LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
            
            List<AppointmentEntity> todayAppointments = appointmentService.getAppointmentsByDateRange(startOfDay, endOfDay);
            return ResponseEntity.ok(todayAppointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/appointments/week")
    public ResponseEntity<List<AppointmentEntity>> getWeekAppointments() {
        try {
            LocalDateTime startOfWeek = LocalDate.now().atStartOfDay();
            LocalDateTime endOfWeek = startOfWeek.plusDays(7);
            
            List<AppointmentEntity> weekAppointments = appointmentService.getAppointmentsByDateRange(startOfWeek, endOfWeek);
            return ResponseEntity.ok(weekAppointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}