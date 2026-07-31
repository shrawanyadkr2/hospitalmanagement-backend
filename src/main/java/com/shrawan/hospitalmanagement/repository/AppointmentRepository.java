package com.shrawan.hospitalmanagement.repository;

import com.shrawan.hospitalmanagement.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByAppointmentDate(LocalDate date);
    long countByAppointmentDate(LocalDate date);
    long countByStatus(Appointment.Status status);
}
