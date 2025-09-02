package com.fikfit.api.repository;

import com.fikfit.api.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByStudentIdOrderByAppointmentDateAsc(Long studentId);
    
    List<AppointmentEntity> findByStatusOrderByAppointmentDateAsc(AppointmentEntity.AppointmentStatus status);
    
    List<AppointmentEntity> findByTypeOrderByAppointmentDateAsc(AppointmentEntity.AppointmentType type);
    
    @Query("SELECT a FROM AppointmentEntity a WHERE a.appointmentDate BETWEEN :startDate AND :endDate ORDER BY a.appointmentDate ASC")
    List<AppointmentEntity> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT a FROM AppointmentEntity a WHERE a.student.id = :studentId AND a.appointmentDate BETWEEN :startDate AND :endDate ORDER BY a.appointmentDate ASC")
    List<AppointmentEntity> findByStudentIdAndDateRange(@Param("studentId") Long studentId,
                                                       @Param("startDate") LocalDateTime startDate,
                                                       @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT a FROM AppointmentEntity a WHERE a.student.id = :studentId AND a.status = :status ORDER BY a.appointmentDate ASC")
    List<AppointmentEntity> findByStudentIdAndStatus(@Param("studentId") Long studentId,
                                                     @Param("status") AppointmentEntity.AppointmentStatus status);
    
    @Query("SELECT a FROM AppointmentEntity a WHERE a.appointmentDate >= :currentDate ORDER BY a.appointmentDate ASC")
    List<AppointmentEntity> findUpcomingAppointments(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT a FROM AppointmentEntity a WHERE a.student.id = :studentId AND a.appointmentDate >= :currentDate ORDER BY a.appointmentDate ASC")
    List<AppointmentEntity> findUpcomingAppointmentsByStudentId(@Param("studentId") Long studentId,
                                                               @Param("currentDate") LocalDateTime currentDate);
}