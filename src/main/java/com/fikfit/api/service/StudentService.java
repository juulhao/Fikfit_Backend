package com.fikfit.api.service;

import com.fikfit.api.entity.StudentEntity;
import com.fikfit.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentEntity createStudent(StudentEntity student) {
        return studentRepository.save(student);
    }

    public StudentEntity getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }
}
