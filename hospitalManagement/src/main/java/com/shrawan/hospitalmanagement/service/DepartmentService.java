package com.shrawan.hospitalmanagement.service;

import com.shrawan.hospitalmanagement.entity.Department;
import com.shrawan.hospitalmanagement.exception.InvalidOperationException;
import com.shrawan.hospitalmanagement.exception.ResourceAlreadyExistsException;
import com.shrawan.hospitalmanagement.exception.ResourceNotFoundException;
import com.shrawan.hospitalmanagement.repository.DepartmentRepository;
import com.shrawan.hospitalmanagement.repository.DoctorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    public DepartmentService(DepartmentRepository departmentRepository, DoctorRepository doctorRepository) {
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public Department createDepartment(Department department) {
        if (departmentRepository.existsByName(department.getName())) {
            throw new ResourceAlreadyExistsException("Department with name " + department.getName() + " already exists");
        }
        if (departmentRepository.existsByCode(department.getCode())) {
            throw new ResourceAlreadyExistsException("Department code " + department.getCode() + " already exists");
        }
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Page<Department> getDepartmentsPaginated(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    @Transactional
    public Department updateDepartment(Long id, Department details) {
        Department department = getDepartmentById(id);
        department.setName(details.getName());
        department.setDescription(details.getDescription());
        department.setCode(details.getCode());
        return departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        Department department = getDepartmentById(id);
        if (!doctorRepository.findByDepartmentId(id).isEmpty()) {
            throw new InvalidOperationException("Cannot delete department because doctors are currently assigned to this department.");
        }
        departmentRepository.delete(department);
    }
}
