package com.fikfit.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "measurements")
@Entity
@Getter
@Setter
public class MeasurementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @Column(name = "measurement_date", nullable = false)
    private LocalDate measurementDate;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight; // em kg

    @Column(precision = 5, scale = 2)
    private BigDecimal height; // em cm

    @Column(precision = 5, scale = 2)
    private BigDecimal bodyFat; // percentual de gordura corporal

    @Column(precision = 5, scale = 2)
    private BigDecimal muscleMass; // massa muscular em kg

    @Column(precision = 5, scale = 2)
    private BigDecimal chest; // peito em cm

    @Column(precision = 5, scale = 2)
    private BigDecimal waist; // cintura em cm

    @Column(precision = 5, scale = 2)
    private BigDecimal hip; // quadril em cm

    @Column(precision = 5, scale = 2)
    private BigDecimal rightArm; // braço direito em cm

    @Column(precision = 5, scale = 2)
    private BigDecimal leftArm; // braço esquerdo em cm

    @Column(precision = 5, scale = 2)
    private BigDecimal rightThigh; // coxa direita em cm

    @Column(precision = 5, scale = 2)
    private BigDecimal leftThigh; // coxa esquerda em cm

    @Column(precision = 5, scale = 2)
    private BigDecimal rightCalf; // panturrilha direita em cm

    @Column(precision = 5, scale = 2)
    private BigDecimal leftCalf; // panturrilha esquerda em cm

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}