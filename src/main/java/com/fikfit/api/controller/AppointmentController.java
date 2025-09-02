package com.fikfit.api.controller;

import com.fikfit.api.entity.AppointmentEntity;
import com.fikfit.api.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;
    
    @GetMapping
    public ResponseEntity<List<AppointmentEntity>> getAllAppointments() {
        try {
            List<AppointmentEntity> appointments = appointmentService.getAllAppointments();
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentEntity> getAppointmentById(@PathVariable Long id) {
        try {
            Optional<AppointmentEntity> appointment = appointmentService.getAppointmentById(id);
            return appointment.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AppointmentEntity>> getAppointmentsByStudentId(@PathVariable Long studentId) {
        try {
            List<AppointmentEntity> appointments = appointmentService.getAppointmentsByStudentId(studentId);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AppointmentEntity>> getAppointmentsByStatus(@PathVariable AppointmentEntity.AppointmentStatus status) {
        try {
            List<AppointmentEntity> appointments = appointmentService.getAppointmentsByStatus(status);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<AppointmentEntity>> getAppointmentsByType(@PathVariable AppointmentEntity.AppointmentType type) {
        try {
            List<AppointmentEntity> appointments = appointmentService.getAppointmentsByType(type);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/range")
    public ResponseEntity<List<AppointmentEntity>> getAppointmentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<AppointmentEntity> appointments = appointmentService.getAppointmentsByDateRange(startDate, endDate);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/student/{studentId}/range")
    public ResponseEntity<List<AppointmentEntity>> getAppointmentsByStudentIdAndDateRange(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<AppointmentEntity> appointments = appointmentService.getAppointmentsByStudentIdAndDateRange(studentId, startDate, endDate);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/student/{studentId}/status/{status}")
    public ResponseEntity<List<AppointmentEntity>> getAppointmentsByStudentIdAndStatus(
            @PathVariable Long studentId,
            @PathVariable AppointmentEntity.AppointmentStatus status) {
        try {
            List<AppointmentEntity> appointments = appointmentService.getAppointmentsByStudentIdAndStatus(studentId, status);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<AppointmentEntity>> getUpcomingAppointments() {
        try {
            List<AppointmentEntity> appointments = appointmentService.getUpcomingAppointments();
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/student/{studentId}/upcoming")
    public ResponseEntity<List<AppointmentEntity>> getUpcomingAppointmentsByStudentId(@PathVariable Long studentId) {
        try {
            List<AppointmentEntity> appointments = appointmentService.getUpcomingAppointmentsByStudentId(studentId);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<AppointmentEntity> createAppointment(@RequestBody AppointmentEntity appointment) {
        try {
            AppointmentEntity createdAppointment = appointmentService.createAppointment(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentEntity> updateAppointment(@PathVariable Long id, @RequestBody AppointmentEntity appointmentDetails) {
        try {
            AppointmentEntity updatedAppointment = appointmentService.updateAppointment(id, appointmentDetails);
            return ResponseEntity.ok(updatedAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<AppointmentEntity> updateAppointmentStatus(
            @PathVariable Long id, 
            @RequestParam AppointmentEntity.AppointmentStatus status) {
        try {
            AppointmentEntity updatedAppointment = appointmentService.updateAppointmentStatus(id, status);
            return ResponseEntity.ok(updatedAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}