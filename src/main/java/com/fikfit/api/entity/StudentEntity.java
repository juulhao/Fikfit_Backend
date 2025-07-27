package com.fikfit.api.entity;

import com.fikfit.api.model.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "student")
@Entity
@Getter
@Setter
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String country;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
}
