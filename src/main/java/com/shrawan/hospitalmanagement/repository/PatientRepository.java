package com.shrawan.hospitalmanagement.repository;

import com.shrawan.hospitalmanagement.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByPatientId(String patientId);
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByUserId(Long userId);
    Boolean existsByEmail(String email);
    Boolean existsByPatientId(String patientId);
}
