package com.fikfit.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fikfit.api.entity.StudentEntity;
import com.fikfit.api.service.StudentService;
import com.fikfit.api.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    private final StudentService studentService;
    private final UserService userService;

    @Autowired
    public StudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    public static class CreateStudentRequest {
        public String name;
        public String email;
        public String phone;
        public String address;
        public String city;
        public String state;
        public String country;
        public String gender;
        public String birthDate;
        public Long userId;
    }

    @PostMapping
    public ResponseEntity<StudentEntity> createStudent(@RequestBody CreateStudentRequest request) {
        if (request.name == null || request.name.isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty");
        }
        if (request.email == null || request.email.isEmpty()) {
            throw new IllegalArgumentException("Student email cannot be empty");
        }
        StudentEntity student = new StudentEntity();
        student.setName(request.name);
        student.setEmail(request.email);
        student.setPhone(request.phone);
        student.setAddress(request.address);
        student.setCity(request.city);
        student.setState(request.state);
        student.setCountry(request.country);
        if (request.gender != null) {
            student.setGender(com.fikfit.api.model.Gender.valueOf(request.gender));
        }
        if (request.birthDate != null) {
            student.setBirthDate(LocalDate.parse(request.birthDate));
        }
        student.setCreatedAt(LocalDate.now());
        if (request.userId != null) {
            userService.findById(request.userId).ifPresentOrElse(
                student::setUser,
                () -> { throw new IllegalArgumentException("User not found for userId: " + request.userId); }
            );
        } else {
            throw new IllegalArgumentException("userId is required to associate a user with the student");
        }
        StudentEntity created = studentService.createStudent(student);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentEntity> getStudentById(@PathVariable Long id) {
        StudentEntity student = studentService.getStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentEntity>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentEntity> updateStudent(@PathVariable Long id, @RequestBody StudentEntity studentData) {
        try {
            StudentEntity updatedStudent = studentService.updateStudent(id, studentData);
            return ResponseEntity.ok(updatedStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
