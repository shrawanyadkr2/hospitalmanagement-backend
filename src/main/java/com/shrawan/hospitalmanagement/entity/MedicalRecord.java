package com.shrawan.hospitalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String tests;

    @Column(columnDefinition = "TEXT")
    private String reports;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "file_url", columnDefinition = "TEXT")
    private String fileUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})
    private Patient patient;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public MedicalRecord() {}

    public MedicalRecord(Long id, String diagnosis, String tests, String reports, String notes, String fileUrl, Doctor doctor, Patient patient, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.diagnosis = diagnosis;
        this.tests = tests;
        this.reports = reports;
        this.notes = notes;
        this.fileUrl = fileUrl;
        this.doctor = doctor;
        this.patient = patient;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getTests() { return tests; }
    public void setTests(String tests) { this.tests = tests; }
    public String getReports() { return reports; }
    public void setReports(String reports) { this.reports = reports; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
