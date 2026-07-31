package com.shrawan.hospitalmanagement.service;

import com.shrawan.hospitalmanagement.dto.DoctorRequest;
import com.shrawan.hospitalmanagement.entity.Department;
import com.shrawan.hospitalmanagement.entity.Doctor;
import com.shrawan.hospitalmanagement.exception.ResourceNotFoundException;
import com.shrawan.hospitalmanagement.repository.AppointmentRepository;
import com.shrawan.hospitalmanagement.repository.DoctorRepository;
import com.shrawan.hospitalmanagement.repository.MedicalRecordRepository;
import com.shrawan.hospitalmanagement.repository.PrescriptionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentService departmentService;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public DoctorService(
            DoctorRepository doctorRepository,
            DepartmentService departmentService,
            AppointmentRepository appointmentRepository,
            PrescriptionRepository prescriptionRepository,
            MedicalRecordRepository medicalRecordRepository
    ) {
        this.doctorRepository = doctorRepository;
        this.departmentService = departmentService;
        this.appointmentRepository = appointmentRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Transactional
    public Doctor createDoctor(DoctorRequest request) {
        Department department = departmentService.getDepartmentById(request.getDepartmentId());

        String generatedDoctorCode = "DOC-" + (1000 + new Random().nextInt(9000));

        Doctor doctor = new Doctor();
        doctor.setDoctorCode(generatedDoctorCode);
        doctor.setFullName(request.getFullName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setExperience(request.getExperience());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setDepartment(department);
        doctor.setAvailability(request.getAvailability() != null ? request.getAvailability() : "Mon-Fri 09:00-17:00");
        doctor.setImageUrl(request.getImageUrl());
        doctor.setAvailable(request.getAvailable() != null ? request.getAvailable() : true);

        return doctorRepository.save(doctor);
    }

    public Page<Doctor> getAllDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }

    public List<Doctor> getDoctorsByDepartment(Long departmentId) {
        return doctorRepository.findByDepartmentId(departmentId);
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
    }

    @Transactional
    public Doctor updateDoctor(Long id, DoctorRequest request) {
        Doctor doctor = getDoctorById(id);
        Department department = departmentService.getDepartmentById(request.getDepartmentId());

        doctor.setFullName(request.getFullName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setExperience(request.getExperience());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setDepartment(department);
        doctor.setAvailability(request.getAvailability());
        if (request.getImageUrl() != null) {
            doctor.setImageUrl(request.getImageUrl());
        }
        if (request.getAvailable() != null) {
            doctor.setAvailable(request.getAvailable());
        }
        return doctorRepository.save(doctor);
    }

    @Transactional
    public void deleteDoctor(Long id) {
        Doctor doctor = getDoctorById(id);
        boolean hasAppointments = !appointmentRepository.findByDoctorId(id).isEmpty();
        boolean hasPrescriptions = !prescriptionRepository.findByDoctorId(id).isEmpty();
        boolean hasRecords = !medicalRecordRepository.findByDoctorId(id).isEmpty();

        if (hasAppointments || hasPrescriptions || hasRecords) {
            // Soft delete / deactivate profile to preserve foreign key integrity & patient history
            doctor.setAvailable(false);
            doctorRepository.save(doctor);
        } else {
            doctorRepository.delete(doctor);
        }
    }
}
