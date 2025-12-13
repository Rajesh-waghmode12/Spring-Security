package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.UserEntity;

public interface UserRepo extends JpaRepository<Long, UserEntity> {
    
    Optional<UserEntity> findByUsername(String username);

    void save(UserEntity user);
}
