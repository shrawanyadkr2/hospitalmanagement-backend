package com.shrawan.hospitalmanagement.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class BillRequest {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    private Long appointmentId;

    private BigDecimal consultationFee;

    private BigDecimal medicineCharges;

    private BigDecimal roomCharges;

    private BigDecimal labCharges;

    private BigDecimal otherCharges;

    private BigDecimal gst;

    private BigDecimal discount;

    public BillRequest() {}

    public BillRequest(Long patientId, Long appointmentId, BigDecimal consultationFee, BigDecimal medicineCharges, BigDecimal roomCharges, BigDecimal labCharges, BigDecimal otherCharges, BigDecimal gst, BigDecimal discount) {
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.consultationFee = consultationFee;
        this.medicineCharges = medicineCharges;
        this.roomCharges = roomCharges;
        this.labCharges = labCharges;
        this.otherCharges = otherCharges;
        this.gst = gst;
        this.discount = discount;
    }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
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
}
