package com.fikfit.api.repository;

import com.fikfit.api.entity.MeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementRepository extends JpaRepository<MeasurementEntity, Long> {
    List<MeasurementEntity> findByStudentIdOrderByMeasurementDateDesc(Long studentId);
    
    @Query("SELECT m FROM MeasurementEntity m WHERE m.student.id = :studentId AND m.measurementDate BETWEEN :startDate AND :endDate ORDER BY m.measurementDate DESC")
    List<MeasurementEntity> findByStudentIdAndDateRange(@Param("studentId") Long studentId, 
                                                       @Param("startDate") LocalDate startDate, 
                                                       @Param("endDate") LocalDate endDate);
    
    @Query("SELECT m FROM MeasurementEntity m WHERE m.student.id = :studentId ORDER BY m.measurementDate DESC LIMIT 1")
    Optional<MeasurementEntity> findLatestByStudentId(@Param("studentId") Long studentId);
    
    @Query("SELECT m FROM MeasurementEntity m WHERE m.student.id = :studentId ORDER BY m.measurementDate ASC LIMIT 1")
    Optional<MeasurementEntity> findFirstByStudentId(@Param("studentId") Long studentId);
}