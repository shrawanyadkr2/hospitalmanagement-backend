package com.shrawan.hospitalmanagement.service;

import com.shrawan.hospitalmanagement.entity.Doctor;
import com.shrawan.hospitalmanagement.entity.MedicalRecord;
import com.shrawan.hospitalmanagement.entity.Patient;
import com.shrawan.hospitalmanagement.exception.ResourceNotFoundException;
import com.shrawan.hospitalmanagement.repository.MedicalRecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public MedicalRecordService(
            MedicalRecordRepository medicalRecordRepository,
            PatientService patientService,
            DoctorService doctorService
    ) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @Transactional
    public MedicalRecord createMedicalRecord(Long doctorId, Long patientId, MedicalRecord record) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Patient patient = patientService.getPatientEntityById(patientId);

        record.setDoctor(doctor);
        record.setPatient(patient);

        return medicalRecordRepository.save(record);
    }

    public Page<MedicalRecord> getAllMedicalRecords(Pageable pageable) {
        return medicalRecordRepository.findAll(pageable);
    }

    public List<MedicalRecord> getRecordsByPatient(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }

    public List<MedicalRecord> getRecordsByDoctor(Long doctorId) {
        return medicalRecordRepository.findByDoctorId(doctorId);
    }

    public MedicalRecord getRecordById(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical record not found with id: " + id));
    }

    @Transactional
    public MedicalRecord updateMedicalRecord(Long id, MedicalRecord details) {
        MedicalRecord record = getRecordById(id);
        record.setDiagnosis(details.getDiagnosis());
        record.setTests(details.getTests());
        record.setReports(details.getReports());
        record.setNotes(details.getNotes());
        if (details.getFileUrl() != null) {
            record.setFileUrl(details.getFileUrl());
        }
        return medicalRecordRepository.save(record);
    }

    @Transactional
    public void deleteMedicalRecord(Long id) {
        MedicalRecord record = getRecordById(id);
        medicalRecordRepository.delete(record);
    }
}
