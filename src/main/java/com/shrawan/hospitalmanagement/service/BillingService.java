package com.shrawan.hospitalmanagement.service;

import com.shrawan.hospitalmanagement.dto.BillRequest;
import com.shrawan.hospitalmanagement.entity.Appointment;
import com.shrawan.hospitalmanagement.entity.Bill;
import com.shrawan.hospitalmanagement.entity.Patient;
import com.shrawan.hospitalmanagement.exception.ResourceNotFoundException;
import com.shrawan.hospitalmanagement.repository.BillRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@Service
public class BillingService {

    private final BillRepository billRepository;
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final EmailService emailService;

    public BillingService(
            BillRepository billRepository,
            PatientService patientService,
            AppointmentService appointmentService,
            EmailService emailService
    ) {
        this.billRepository = billRepository;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.emailService = emailService;
    }

    @Transactional
    public Bill generateBill(BillRequest request) {
        Patient patient = patientService.getPatientEntityById(request.getPatientId());
        Appointment appointment = request.getAppointmentId() != null ? appointmentService.getAppointmentById(request.getAppointmentId()) : null;

        BigDecimal consultation = request.getConsultationFee() != null ? request.getConsultationFee() : BigDecimal.ZERO;
        BigDecimal medicine = request.getMedicineCharges() != null ? request.getMedicineCharges() : BigDecimal.ZERO;
        BigDecimal room = request.getRoomCharges() != null ? request.getRoomCharges() : BigDecimal.ZERO;
        BigDecimal lab = request.getLabCharges() != null ? request.getLabCharges() : BigDecimal.ZERO;
        BigDecimal other = request.getOtherCharges() != null ? request.getOtherCharges() : BigDecimal.ZERO;
        BigDecimal gst = request.getGst() != null ? request.getGst() : BigDecimal.ZERO;
        BigDecimal discount = request.getDiscount() != null ? request.getDiscount() : BigDecimal.ZERO;

        BigDecimal subtotal = consultation.add(medicine).add(room).add(lab).add(other);
        BigDecimal gstAmount = subtotal.multiply(gst).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        BigDecimal grandTotal = subtotal.add(gstAmount).subtract(discount);
        if (grandTotal.compareTo(BigDecimal.ZERO) < 0) {
            grandTotal = BigDecimal.ZERO;
        }

        String billNumber = "BILL-" + (100000 + new Random().nextInt(900000));

        Bill bill = new Bill();
        bill.setBillNumber(billNumber);
        bill.setPatient(patient);
        bill.setAppointment(appointment);
        bill.setConsultationFee(consultation);
        bill.setMedicineCharges(medicine);
        bill.setRoomCharges(room);
        bill.setLabCharges(lab);
        bill.setOtherCharges(other);
        bill.setGst(gst);
        bill.setDiscount(discount);
        bill.setGrandTotal(grandTotal);
        bill.setPaymentStatus(Bill.PaymentStatus.PENDING);

        Bill saved = billRepository.save(bill);

        emailService.sendBillGeneratedEmail(patient.getEmail(), saved.getBillNumber(), saved.getGrandTotal().toString());

        return saved;
    }

    public Page<Bill> getAllBills(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    public List<Bill> getBillsByPatient(Long patientId) {
        return billRepository.findByPatientId(patientId);
    }

    public Bill getBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + id));
    }

    public Bill getBillByNumber(String billNumber) {
        return billRepository.findByBillNumber(billNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with bill number: " + billNumber));
    }

    @Transactional
    public Bill updatePaymentStatus(Long billId, Bill.PaymentStatus status) {
        Bill bill = getBillById(billId);
        bill.setPaymentStatus(status);
        return billRepository.save(bill);
    }
}
