package com.shrawan.hospitalmanagement.service;

import com.shrawan.hospitalmanagement.dto.PatientRequest;
import com.shrawan.hospitalmanagement.dto.PatientResponse;
import com.shrawan.hospitalmanagement.entity.Patient;
import com.shrawan.hospitalmanagement.exception.InvalidOperationException;
import com.shrawan.hospitalmanagement.exception.ResourceAlreadyExistsException;
import com.shrawan.hospitalmanagement.exception.ResourceNotFoundException;
import com.shrawan.hospitalmanagement.repository.AppointmentRepository;
import com.shrawan.hospitalmanagement.repository.BillRepository;
import com.shrawan.hospitalmanagement.repository.PatientRepository;
import com.shrawan.hospitalmanagement.repository.PrescriptionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final BillRepository billRepository;

    public PatientService(
            PatientRepository patientRepository,
            AppointmentRepository appointmentRepository,
            PrescriptionRepository prescriptionRepository,
            BillRepository billRepository
    ) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.billRepository = billRepository;
    }

    @Transactional
    public PatientResponse createPatient(PatientRequest request) {
        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Patient already exists with email: " + request.getEmail());
        }

        String generatedPatientId = "PAT-" + (100000 + new Random().nextInt(900000));

        Patient patient = new Patient();
        patient.setPatientId(generatedPatientId);
        patient.setFullName(request.getFullName());
        patient.setGender(request.getGender());
        patient.setDob(request.getDob());
        patient.setAge(request.getAge());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setEmergencyContact(request.getEmergencyContact());
        patient.setAllergies(request.getAllergies());
        patient.setMedicalHistory(request.getMedicalHistory());
        patient.setProfileImage(request.getProfileImage());

        Patient saved = patientRepository.save(patient);
        return mapToResponse(saved);
    }

    public Page<PatientResponse> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable).map(this::mapToResponse);
    }

    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return mapToResponse(patient);
    }

    public Patient getPatientEntityById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    public PatientResponse getPatientByPatientId(String patientId) {
        Patient patient = patientRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with patientId: " + patientId));
        return mapToResponse(patient);
    }

    @Transactional
    public PatientResponse updatePatient(Long id, PatientRequest request) {
        Patient patient = getPatientEntityById(id);
        patient.setFullName(request.getFullName());
        patient.setGender(request.getGender());
        patient.setDob(request.getDob());
        patient.setAge(request.getAge());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setEmergencyContact(request.getEmergencyContact());
        patient.setAllergies(request.getAllergies());
        patient.setMedicalHistory(request.getMedicalHistory());
        if (request.getProfileImage() != null) {
            patient.setProfileImage(request.getProfileImage());
        }
        return mapToResponse(patientRepository.save(patient));
    }

    @Transactional
    public void deletePatient(Long id) {
        Patient patient = getPatientEntityById(id);
        boolean hasAppointments = !appointmentRepository.findByPatientId(id).isEmpty();
        boolean hasPrescriptions = !prescriptionRepository.findByPatientId(id).isEmpty();
        boolean hasBills = !billRepository.findByPatientId(id).isEmpty();

        if (hasAppointments || hasPrescriptions || hasBills) {
            throw new InvalidOperationException("Cannot delete patient because active appointments, prescriptions, or bills are linked to this patient profile.");
        }
        patientRepository.delete(patient);
    }

    public PatientResponse mapToResponse(Patient patient) {
        PatientResponse response = new PatientResponse();
        response.setId(patient.getId());
        response.setPatientId(patient.getPatientId());
        response.setFullName(patient.getFullName());
        response.setGender(patient.getGender());
        response.setDob(patient.getDob());
        response.setAge(patient.getAge());
        response.setBloodGroup(patient.getBloodGroup());
        response.setPhone(patient.getPhone());
        response.setEmail(patient.getEmail());
        response.setAddress(patient.getAddress());
        response.setEmergencyContact(patient.getEmergencyContact());
        response.setAllergies(patient.getAllergies());
        response.setMedicalHistory(patient.getMedicalHistory());
        response.setProfileImage(patient.getProfileImage());
        response.setCreatedAt(patient.getCreatedAt());
        response.setUpdatedAt(patient.getUpdatedAt());
        return response;
    }
}
