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

    public StudentEntity updateStudent(Long id, StudentEntity studentData) {
        StudentEntity existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));
        
        existingStudent.setName(studentData.getName());
        existingStudent.setEmail(studentData.getEmail());
        existingStudent.setPhone(studentData.getPhone());
        existingStudent.setAddress(studentData.getAddress());
        existingStudent.setCity(studentData.getCity());
        existingStudent.setState(studentData.getState());
        existingStudent.setCountry(studentData.getCountry());
        existingStudent.setGender(studentData.getGender());
        existingStudent.setBirthDate(studentData.getBirthDate());
        existingStudent.setUpdatedAt(java.time.LocalDate.now());
        
        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
}
