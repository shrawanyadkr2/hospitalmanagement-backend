package com.shrawan.hospitalmanagement.service;

import com.shrawan.hospitalmanagement.dto.DashboardResponse;
import com.shrawan.hospitalmanagement.entity.Appointment;
import com.shrawan.hospitalmanagement.entity.Bill;
import com.shrawan.hospitalmanagement.repository.AppointmentRepository;
import com.shrawan.hospitalmanagement.repository.BillRepository;
import com.shrawan.hospitalmanagement.repository.DoctorRepository;
import com.shrawan.hospitalmanagement.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class DashboardService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final BillRepository billRepository;

    public DashboardService(
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository,
            BillRepository billRepository
    ) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.billRepository = billRepository;
    }

    public DashboardResponse getDashboardStatistics() {
        long totalPatients = patientRepository.count();
        long totalDoctors = doctorRepository.count();
        long todaysAppointments = appointmentRepository.countByAppointmentDate(LocalDate.now());
        long completedAppointments = appointmentRepository.countByStatus(Appointment.Status.COMPLETED);
        long availableDoctors = doctorRepository.countByAvailable(true);
        long pendingBills = billRepository.countByPaymentStatus(Bill.PaymentStatus.PENDING);
        BigDecimal revenue = billRepository.sumGrandTotalByPaidStatus();

        return new DashboardResponse(
                totalPatients,
                totalDoctors,
                todaysAppointments,
                revenue != null ? revenue : BigDecimal.ZERO,
                pendingBills,
                completedAppointments,
                availableDoctors
        );
    }
}
