package com.shrawan.hospitalmanagement.repository;

import com.shrawan.hospitalmanagement.entity.Role;
import com.shrawan.hospitalmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Optional<User> findByVerificationToken(String token);
    Optional<User> findByResetToken(String token);
    List<User> findByRole(Role role);
}
