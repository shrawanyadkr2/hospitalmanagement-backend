package com.shrawan.hospitalmanagement.repository;

import com.shrawan.hospitalmanagement.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByBillNumber(String billNumber);
    List<Bill> findByPatientId(Long patientId);
    List<Bill> findByPaymentStatus(Bill.PaymentStatus status);
    long countByPaymentStatus(Bill.PaymentStatus status);

    @Query("SELECT COALESCE(SUM(b.grandTotal), 0) FROM Bill b WHERE b.paymentStatus = 'PAID'")
    BigDecimal sumGrandTotalByPaidStatus();
}
