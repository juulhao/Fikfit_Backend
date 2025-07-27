package com.fikfit.api.controller;

import com.fikfit.api.entity.ProfileEntity;
import com.fikfit.api.entity.UserEntity;
import com.fikfit.api.service.ProfileService;
import com.fikfit.api.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;

    @Autowired
    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String username = claims.getSubject();
        UserEntity user = userService.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return profileService.getByUserId(user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProfileEntity> createOrUpdateProfile(HttpServletRequest request, @RequestBody ProfileEntity profileData) {
        Claims claims = (Claims) request.getAttribute("claims");
        String username = claims.getSubject();
        UserEntity user = userService.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        ProfileEntity profile = profileService.createOrUpdateProfile(user.getId(), profileData);
        return ResponseEntity.ok(profile);
    }
}

