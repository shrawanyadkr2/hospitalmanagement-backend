package com.shrawan.hospitalmanagement.dto;

import com.shrawan.hospitalmanagement.entity.Appointment;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentRequest {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotNull(message = "Appointment date is required")
    private LocalDate appointmentDate;

    @NotNull(message = "Appointment time is required")
    private LocalTime appointmentTime;

    private String reason;

    private Appointment.Status status;

    public AppointmentRequest() {}

    public AppointmentRequest(Long patientId, Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime, String reason, Appointment.Status status) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.status = status;
    }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Appointment.Status getStatus() { return status; }
    public void setStatus(Appointment.Status status) { this.status = status; }
}
