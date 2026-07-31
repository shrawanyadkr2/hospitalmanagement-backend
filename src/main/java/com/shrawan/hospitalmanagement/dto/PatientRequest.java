package com.shrawan.hospitalmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class PatientRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Gender is required")
    private String gender;

    private LocalDate dob;

    @NotNull(message = "Age is required")
    @Positive(message = "Age must be positive")
    private Integer age;

    private String bloodGroup;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String address;

    private String emergencyContact;

    private String allergies;

    private String medicalHistory;

    private String profileImage;

    public PatientRequest() {}

    public PatientRequest(String fullName, String gender, LocalDate dob, Integer age, String bloodGroup, String phone, String email, String address, String emergencyContact, String allergies, String medicalHistory, String profileImage) {
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
    }

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
}
