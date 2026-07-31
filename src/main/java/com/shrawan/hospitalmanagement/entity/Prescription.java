package com.shrawan.hospitalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String medicine;

    private String dosage;

    private String duration;

    @Column(columnDefinition = "TEXT")
    private String advice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})
    private Patient patient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "patient", "doctor"})
    private Appointment appointment;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Prescription() {}

    public Prescription(Long id, String medicine, String dosage, String duration, String advice, Doctor doctor, Patient patient, Appointment appointment, String pdfUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.medicine = medicine;
        this.dosage = dosage;
        this.duration = duration;
        this.advice = advice;
        this.doctor = doctor;
        this.patient = patient;
        this.appointment = appointment;
        this.pdfUrl = pdfUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMedicine() { return medicine; }
    public void setMedicine(String medicine) { this.medicine = medicine; }
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getAdvice() { return advice; }
    public void setAdvice(String advice) { this.advice = advice; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
    public String getPdfUrl() { return pdfUrl; }
    public void setPdfUrl(String pdfUrl) { this.pdfUrl = pdfUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
