package com.fikfit.api.service;

import com.fikfit.api.entity.AppointmentEntity;
import com.fikfit.api.repository.AppointmentRepository;
import com.fikfit.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<AppointmentEntity> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    public Optional<AppointmentEntity> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }
    
    public List<AppointmentEntity> getAppointmentsByStudentId(Long studentId) {
        return appointmentRepository.findByStudentIdOrderByAppointmentDateAsc(studentId);
    }
    
    public List<AppointmentEntity> getAppointmentsByStatus(AppointmentEntity.AppointmentStatus status) {
        return appointmentRepository.findByStatusOrderByAppointmentDateAsc(status);
    }
    
    public List<AppointmentEntity> getAppointmentsByType(AppointmentEntity.AppointmentType type) {
        return appointmentRepository.findByTypeOrderByAppointmentDateAsc(type);
    }
    
    public List<AppointmentEntity> getAppointmentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findByDateRange(startDate, endDate);
    }
    
    public List<AppointmentEntity> getAppointmentsByStudentIdAndDateRange(Long studentId, LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findByStudentIdAndDateRange(studentId, startDate, endDate);
    }
    
    public List<AppointmentEntity> getAppointmentsByStudentIdAndStatus(Long studentId, AppointmentEntity.AppointmentStatus status) {
        return appointmentRepository.findByStudentIdAndStatus(studentId, status);
    }
    
    public List<AppointmentEntity> getUpcomingAppointments() {
        return appointmentRepository.findUpcomingAppointments(LocalDateTime.now());
    }
    
    public List<AppointmentEntity> getUpcomingAppointmentsByStudentId(Long studentId) {
        return appointmentRepository.findUpcomingAppointmentsByStudentId(studentId, LocalDateTime.now());
    }
    
    public AppointmentEntity createAppointment(AppointmentEntity appointment) {
        // Verificar se o estudante existe
        if (appointment.getStudent() != null && appointment.getStudent().getId() != null) {
            studentRepository.findById(appointment.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
        }
        
        // Validar data do agendamento
        if (appointment.getAppointmentDate() != null && appointment.getAppointmentDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível agendar para uma data no passado");
        }
        
        // Definir status padrão se não fornecido
        if (appointment.getStatus() == null) {
            appointment.setStatus(AppointmentEntity.AppointmentStatus.SCHEDULED);
        }
        
        return appointmentRepository.save(appointment);
    }
    
    public AppointmentEntity updateAppointment(Long id, AppointmentEntity appointmentDetails) {
        AppointmentEntity appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        
        // Atualizar campos básicos
        if (appointmentDetails.getAppointmentDate() != null) {
            // Validar nova data
            if (appointmentDetails.getAppointmentDate().isBefore(LocalDateTime.now()) && 
                !appointment.getStatus().equals(AppointmentEntity.AppointmentStatus.COMPLETED)) {
                throw new RuntimeException("Não é possível agendar para uma data no passado");
            }
            appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
        }
        
        if (appointmentDetails.getType() != null) {
            appointment.setType(appointmentDetails.getType());
        }
        
        if (appointmentDetails.getStatus() != null) {
            appointment.setStatus(appointmentDetails.getStatus());
        }
        
        if (appointmentDetails.getTitle() != null) {
            appointment.setTitle(appointmentDetails.getTitle());
        }
        
        if (appointmentDetails.getDescription() != null) {
            appointment.setDescription(appointmentDetails.getDescription());
        }
        
        if (appointmentDetails.getObservations() != null) {
            appointment.setObservations(appointmentDetails.getObservations());
        }
        
        // Atualizar estudante se fornecido
        if (appointmentDetails.getStudent() != null && appointmentDetails.getStudent().getId() != null) {
            studentRepository.findById(appointmentDetails.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
            appointment.setStudent(appointmentDetails.getStudent());
        }
        
        return appointmentRepository.save(appointment);
    }
    
    public AppointmentEntity updateAppointmentStatus(Long id, AppointmentEntity.AppointmentStatus status) {
        AppointmentEntity appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }
    
    public void deleteAppointment(Long id) {
        AppointmentEntity appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        
        appointmentRepository.delete(appointment);
    }
}