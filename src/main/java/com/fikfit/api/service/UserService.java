package com.fikfit.api.service;

import com.fikfit.api.entity.UserEntity;
import com.fikfit.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(UserEntity user) {
        userRepository.save(user);
    }

    public Optional<UserEntity> findById(Long userId) {
        return userRepository.findById(userId);
    }
}

