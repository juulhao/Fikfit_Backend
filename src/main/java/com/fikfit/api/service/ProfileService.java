package com.fikfit.api.service;

import com.fikfit.api.entity.ProfileEntity;
import com.fikfit.api.entity.UserEntity;
import com.fikfit.api.repository.ProfileRepository;
import com.fikfit.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public Optional<ProfileEntity> getByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
    }

    public ProfileEntity createOrUpdateProfile(Long userId, ProfileEntity profileData) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Optional<ProfileEntity> existing = profileRepository.findByUser(user);
        ProfileEntity profile = existing.orElse(new ProfileEntity());
        profile.setUser(user);
        profile.setFullName(profileData.getFullName());
        profile.setPhone(profileData.getPhone());
        profile.setAddress(profileData.getAddress());
        profile.setCity(profileData.getCity());
        profile.setState(profileData.getState());
        profile.setCountry(profileData.getCountry());
        profile.setAvatarUrl(profileData.getAvatarUrl());
        return profileRepository.save(profile);
    }
}

