package com.shrawan.hospitalmanagement.service;

import com.shrawan.hospitalmanagement.entity.Appointment;
import com.shrawan.hospitalmanagement.entity.Doctor;
import com.shrawan.hospitalmanagement.entity.Patient;
import com.shrawan.hospitalmanagement.entity.Prescription;
import com.shrawan.hospitalmanagement.exception.ResourceNotFoundException;
import com.shrawan.hospitalmanagement.repository.PrescriptionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    public PrescriptionService(
            PrescriptionRepository prescriptionRepository,
            PatientService patientService,
            DoctorService doctorService,
            AppointmentService appointmentService
    ) {
        this.prescriptionRepository = prescriptionRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    @Transactional
    public Prescription createPrescription(Long doctorId, Long patientId, Long appointmentId, Prescription prescription) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Patient patient = patientService.getPatientEntityById(patientId);
        Appointment appointment = appointmentId != null ? appointmentService.getAppointmentById(appointmentId) : null;

        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setAppointment(appointment);

        return prescriptionRepository.save(prescription);
    }

    public Page<Prescription> getAllPrescriptions(Pageable pageable) {
        return prescriptionRepository.findAll(pageable);
    }

    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    public List<Prescription> getPrescriptionsByDoctor(Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId);
    }

    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + id));
    }

    @Transactional
    public Prescription updatePrescription(Long id, Prescription details) {
        Prescription prescription = getPrescriptionById(id);
        prescription.setMedicine(details.getMedicine());
        prescription.setDosage(details.getDosage());
        prescription.setDuration(details.getDuration());
        prescription.setAdvice(details.getAdvice());
        if (details.getPdfUrl() != null) {
            prescription.setPdfUrl(details.getPdfUrl());
        }
        return prescriptionRepository.save(prescription);
    }

    @Transactional
    public void deletePrescription(Long id) {
        Prescription prescription = getPrescriptionById(id);
        prescriptionRepository.delete(prescription);
    }
}
