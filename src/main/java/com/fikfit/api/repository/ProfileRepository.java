package com.fikfit.api.repository;

import com.fikfit.api.entity.ProfileEntity;
import com.fikfit.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    Optional<ProfileEntity> findByUser(UserEntity user);
    Optional<ProfileEntity> findByUserId(Long userId);
}

