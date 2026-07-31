package com.shrawan.hospitalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bills")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bill {

    public enum PaymentStatus {
        PENDING,
        PAID,
        CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bill_number", nullable = false, unique = true)
    private String billNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "patient", "doctor"})
    private Appointment appointment;

    @Column(name = "consultation_fee")
    private BigDecimal consultationFee;

    @Column(name = "medicine_charges")
    private BigDecimal medicineCharges;

    @Column(name = "room_charges")
    private BigDecimal roomCharges;

    @Column(name = "lab_charges")
    private BigDecimal labCharges;

    @Column(name = "other_charges")
    private BigDecimal otherCharges;

    private BigDecimal gst;

    private BigDecimal discount;

    @Column(name = "grand_total", nullable = false)
    private BigDecimal grandTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Bill() {}

    public Bill(Long id, String billNumber, Patient patient, Appointment appointment, BigDecimal consultationFee, BigDecimal medicineCharges, BigDecimal roomCharges, BigDecimal labCharges, BigDecimal otherCharges, BigDecimal gst, BigDecimal discount, BigDecimal grandTotal, PaymentStatus paymentStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.billNumber = billNumber;
        this.patient = patient;
        this.appointment = appointment;
        this.consultationFee = consultationFee;
        this.medicineCharges = medicineCharges;
        this.roomCharges = roomCharges;
        this.labCharges = labCharges;
        this.otherCharges = otherCharges;
        this.gst = gst;
        this.discount = discount;
        this.grandTotal = grandTotal;
        this.paymentStatus = paymentStatus != null ? paymentStatus : PaymentStatus.PENDING;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBillNumber() { return billNumber; }
    public void setBillNumber(String billNumber) { this.billNumber = billNumber; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
    public BigDecimal getConsultationFee() { return consultationFee; }
    public void setConsultationFee(BigDecimal consultationFee) { this.consultationFee = consultationFee; }
    public BigDecimal getMedicineCharges() { return medicineCharges; }
    public void setMedicineCharges(BigDecimal medicineCharges) { this.medicineCharges = medicineCharges; }
    public BigDecimal getRoomCharges() { return roomCharges; }
    public void setRoomCharges(BigDecimal roomCharges) { this.roomCharges = roomCharges; }
    public BigDecimal getLabCharges() { return labCharges; }
    public void setLabCharges(BigDecimal labCharges) { this.labCharges = labCharges; }
    public BigDecimal getOtherCharges() { return otherCharges; }
    public void setOtherCharges(BigDecimal otherCharges) { this.otherCharges = otherCharges; }
    public BigDecimal getGst() { return gst; }
    public void setGst(BigDecimal gst) { this.gst = gst; }
    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }
    public BigDecimal getGrandTotal() { return grandTotal; }
    public void setGrandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
