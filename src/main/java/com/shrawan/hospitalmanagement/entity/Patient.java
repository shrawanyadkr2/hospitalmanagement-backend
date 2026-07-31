package com.shrawan.hospitalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false, unique = true)
    private String patientId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String gender;

    private LocalDate dob;

    private Integer age;

    @Column(name = "blood_group")
    private String bloodGroup;

    private String phone;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "medical_history", columnDefinition = "TEXT")
    private String medicalHistory;

    @Column(name = "profile_image")
    private String profileImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password", "resetToken", "verificationToken"})
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Patient() {}

    public Patient(Long id, String patientId, String fullName, String gender, LocalDate dob, Integer age, String bloodGroup, String phone, String email, String address, String emergencyContact, String allergies, String medicalHistory, String profileImage, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.patientId = patientId;
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.allergies = allergies;
        this.medicalHistory = medicalHistory;
        this.profileImage = profileImage;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }
    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
