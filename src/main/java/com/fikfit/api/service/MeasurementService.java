package com.fikfit.api.service;

import com.fikfit.api.entity.MeasurementEntity;
import com.fikfit.api.repository.MeasurementRepository;
import com.fikfit.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {
    
    @Autowired
    private MeasurementRepository measurementRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<MeasurementEntity> getAllMeasurements() {
        return measurementRepository.findAll();
    }
    
    public Optional<MeasurementEntity> getMeasurementById(Long id) {
        return measurementRepository.findById(id);
    }
    
    public List<MeasurementEntity> getMeasurementsByStudentId(Long studentId) {
        return measurementRepository.findByStudentIdOrderByMeasurementDateDesc(studentId);
    }
    
    public List<MeasurementEntity> getMeasurementsByStudentIdAndDateRange(Long studentId, LocalDate startDate, LocalDate endDate) {
        return measurementRepository.findByStudentIdAndDateRange(studentId, startDate, endDate);
    }
    
    public Optional<MeasurementEntity> getLatestMeasurementByStudentId(Long studentId) {
        return measurementRepository.findLatestByStudentId(studentId);
    }
    
    public Optional<MeasurementEntity> getFirstMeasurementByStudentId(Long studentId) {
        return measurementRepository.findFirstByStudentId(studentId);
    }
    
    public MeasurementEntity createMeasurement(MeasurementEntity measurement) {
        // Verificar se o estudante existe
        if (measurement.getStudent() != null && measurement.getStudent().getId() != null) {
            studentRepository.findById(measurement.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
        }
        
        // Se não foi fornecida uma data, usar a data atual
        if (measurement.getMeasurementDate() == null) {
            measurement.setMeasurementDate(LocalDate.now());
        }
        
        return measurementRepository.save(measurement);
    }
    
    public MeasurementEntity updateMeasurement(Long id, MeasurementEntity measurementDetails) {
        MeasurementEntity measurement = measurementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medida não encontrada"));
        
        // Atualizar campos básicos
        if (measurementDetails.getMeasurementDate() != null) {
            measurement.setMeasurementDate(measurementDetails.getMeasurementDate());
        }
        
        // Atualizar medidas corporais
        if (measurementDetails.getWeight() != null) {
            measurement.setWeight(measurementDetails.getWeight());
        }
        if (measurementDetails.getHeight() != null) {
            measurement.setHeight(measurementDetails.getHeight());
        }
        if (measurementDetails.getBodyFat() != null) {
            measurement.setBodyFat(measurementDetails.getBodyFat());
        }
        if (measurementDetails.getMuscleMass() != null) {
            measurement.setMuscleMass(measurementDetails.getMuscleMass());
        }
        if (measurementDetails.getChest() != null) {
            measurement.setChest(measurementDetails.getChest());
        }
        if (measurementDetails.getWaist() != null) {
            measurement.setWaist(measurementDetails.getWaist());
        }
        if (measurementDetails.getHip() != null) {
            measurement.setHip(measurementDetails.getHip());
        }
        if (measurementDetails.getRightArm() != null) {
            measurement.setRightArm(measurementDetails.getRightArm());
        }
        if (measurementDetails.getLeftArm() != null) {
            measurement.setLeftArm(measurementDetails.getLeftArm());
        }
        if (measurementDetails.getRightThigh() != null) {
            measurement.setRightThigh(measurementDetails.getRightThigh());
        }
        if (measurementDetails.getLeftThigh() != null) {
            measurement.setLeftThigh(measurementDetails.getLeftThigh());
        }
        if (measurementDetails.getRightCalf() != null) {
            measurement.setRightCalf(measurementDetails.getRightCalf());
        }
        if (measurementDetails.getLeftCalf() != null) {
            measurement.setLeftCalf(measurementDetails.getLeftCalf());
        }
        if (measurementDetails.getObservations() != null) {
            measurement.setObservations(measurementDetails.getObservations());
        }
        
        // Atualizar estudante se fornecido
        if (measurementDetails.getStudent() != null && measurementDetails.getStudent().getId() != null) {
            studentRepository.findById(measurementDetails.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
            measurement.setStudent(measurementDetails.getStudent());
        }
        
        return measurementRepository.save(measurement);
    }
    
    public void deleteMeasurement(Long id) {
        MeasurementEntity measurement = measurementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Medida não encontrada"));
        
        measurementRepository.delete(measurement);
    }
}