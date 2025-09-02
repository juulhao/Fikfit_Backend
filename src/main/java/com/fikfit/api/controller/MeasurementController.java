package com.fikfit.api.controller;

import com.fikfit.api.entity.MeasurementEntity;
import com.fikfit.api.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/measurements")
@CrossOrigin(origins = "*")
public class MeasurementController {
    
    @Autowired
    private MeasurementService measurementService;
    
    @GetMapping
    public ResponseEntity<List<MeasurementEntity>> getAllMeasurements() {
        try {
            List<MeasurementEntity> measurements = measurementService.getAllMeasurements();
            return ResponseEntity.ok(measurements);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MeasurementEntity> getMeasurementById(@PathVariable Long id) {
        try {
            Optional<MeasurementEntity> measurement = measurementService.getMeasurementById(id);
            return measurement.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<MeasurementEntity>> getMeasurementsByStudentId(@PathVariable Long studentId) {
        try {
            List<MeasurementEntity> measurements = measurementService.getMeasurementsByStudentId(studentId);
            return ResponseEntity.ok(measurements);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/student/{studentId}/range")
    public ResponseEntity<List<MeasurementEntity>> getMeasurementsByStudentIdAndDateRange(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<MeasurementEntity> measurements = measurementService.getMeasurementsByStudentIdAndDateRange(studentId, startDate, endDate);
            return ResponseEntity.ok(measurements);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/student/{studentId}/latest")
    public ResponseEntity<MeasurementEntity> getLatestMeasurementByStudentId(@PathVariable Long studentId) {
        try {
            Optional<MeasurementEntity> measurement = measurementService.getLatestMeasurementByStudentId(studentId);
            return measurement.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/student/{studentId}/first")
    public ResponseEntity<MeasurementEntity> getFirstMeasurementByStudentId(@PathVariable Long studentId) {
        try {
            Optional<MeasurementEntity> measurement = measurementService.getFirstMeasurementByStudentId(studentId);
            return measurement.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<MeasurementEntity> createMeasurement(@RequestBody MeasurementEntity measurement) {
        try {
            MeasurementEntity createdMeasurement = measurementService.createMeasurement(measurement);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMeasurement);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MeasurementEntity> updateMeasurement(@PathVariable Long id, @RequestBody MeasurementEntity measurementDetails) {
        try {
            MeasurementEntity updatedMeasurement = measurementService.updateMeasurement(id, measurementDetails);
            return ResponseEntity.ok(updatedMeasurement);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeasurement(@PathVariable Long id) {
        try {
            measurementService.deleteMeasurement(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}