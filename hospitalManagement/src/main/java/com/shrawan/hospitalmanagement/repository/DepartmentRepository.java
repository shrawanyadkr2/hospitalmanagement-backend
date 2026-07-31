package com.shrawan.hospitalmanagement.repository;

import com.shrawan.hospitalmanagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
    Optional<Department> findByCode(String code);
    Boolean existsByName(String name);
    Boolean existsByCode(String code);
}
