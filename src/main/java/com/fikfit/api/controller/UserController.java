package com.fikfit.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fikfit.api.entity.UserEntity;
import com.fikfit.api.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity created = userService.createUser(user);
        return ResponseEntity.ok(created);
    }
}
