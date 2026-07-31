package com.shrawan.hospitalmanagement.service;

import com.shrawan.hospitalmanagement.dto.AppointmentRequest;
import com.shrawan.hospitalmanagement.entity.Appointment;
import com.shrawan.hospitalmanagement.entity.Doctor;
import com.shrawan.hospitalmanagement.entity.Patient;
import com.shrawan.hospitalmanagement.exception.ResourceNotFoundException;
import com.shrawan.hospitalmanagement.repository.AppointmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final EmailService emailService;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PatientService patientService,
            DoctorService doctorService,
            EmailService emailService
    ) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.emailService = emailService;
    }

    @Transactional
    public Appointment scheduleAppointment(AppointmentRequest request) {
        Patient patient = patientService.getPatientEntityById(request.getPatientId());
        Doctor doctor = doctorService.getDoctorById(request.getDoctorId());

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus() != null ? request.getStatus() : Appointment.Status.BOOKED);

        Appointment saved = appointmentRepository.save(appointment);

        emailService.sendAppointmentConfirmation(
                patient.getEmail(),
                patient.getFullName(),
                doctor.getFullName(),
                saved.getAppointmentDate().toString(),
                saved.getAppointmentTime().toString()
        );

        return saved;
    }

    public Page<Appointment> getAllAppointments(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }

    @Transactional
    public Appointment updateStatus(Long id, Appointment.Status status) {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment updateAppointment(Long id, AppointmentRequest request) {
        Appointment appointment = getAppointmentById(id);
        if (request.getPatientId() != null) {
            appointment.setPatient(patientService.getPatientEntityById(request.getPatientId()));
        }
        if (request.getDoctorId() != null) {
            appointment.setDoctor(doctorService.getDoctorById(request.getDoctorId()));
        }
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        if (request.getStatus() != null) {
            appointment.setStatus(request.getStatus());
        }
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public void cancelAppointment(Long id) {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus(Appointment.Status.CANCELLED);
        appointmentRepository.save(appointment);
    }
}
