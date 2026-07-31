package com.shrawan.hospitalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "doctors")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_code", nullable = false, unique = true)
    private String doctorCode;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String specialization;

    private String qualification;

    private Integer experience;

    private String email;

    private String phone;

    @Column(name = "consultation_fee", nullable = false)
    private BigDecimal consultationFee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Department department;

    private String availability;

    @Column(name = "image_url")
    private String imageUrl;

    private Boolean available = true;

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

    public Doctor() {}

    public Doctor(Long id, String doctorCode, String fullName, String specialization, String qualification, Integer experience, String email, String phone, BigDecimal consultationFee, Department department, String availability, String imageUrl, Boolean available, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.doctorCode = doctorCode;
        this.fullName = fullName;
        this.specialization = specialization;
        this.qualification = qualification;
        this.experience = experience;
        this.email = email;
        this.phone = phone;
        this.consultationFee = consultationFee;
        this.department = department;
        this.availability = availability;
        this.imageUrl = imageUrl;
        this.available = available != null ? available : true;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDoctorCode() { return doctorCode; }
    public void setDoctorCode(String doctorCode) { this.doctorCode = doctorCode; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public Integer getExperience() { return experience; }
    public void setExperience(Integer experience) { this.experience = experience; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public BigDecimal getConsultationFee() { return consultationFee; }
    public void setConsultationFee(BigDecimal consultationFee) { this.consultationFee = consultationFee; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
