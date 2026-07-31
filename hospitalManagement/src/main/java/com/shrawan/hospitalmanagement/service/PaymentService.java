package com.shrawan.hospitalmanagement.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.shrawan.hospitalmanagement.dto.PaymentRequest;
import com.shrawan.hospitalmanagement.entity.Bill;
import com.shrawan.hospitalmanagement.entity.Payment;
import com.shrawan.hospitalmanagement.repository.PaymentRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final BillingService billingService;
    private final EmailService emailService;
    private final String keyId;
    private final String keySecret;

    public PaymentService(
            PaymentRepository paymentRepository,
            BillingService billingService,
            EmailService emailService,
            @Value("${razorpay.key-id:rzp_test_key}") String keyId,
            @Value("${razorpay.key-secret:rzp_test_secret}") String keySecret
    ) {
        this.paymentRepository = paymentRepository;
        this.billingService = billingService;
        this.emailService = emailService;
        this.keyId = keyId;
        this.keySecret = keySecret;
    }

    @Transactional
    public Payment createRazorpayOrder(Long billId, BigDecimal amount) {
        Bill bill = billingService.getBillById(billId);

        String orderId;
        try {
            RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount.multiply(new BigDecimal("100")).longValue());
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", bill.getBillNumber());

            Order order = razorpay.orders.create(orderRequest);
            orderId = order.get("id");
        } catch (Exception e) {
            log.error("Razorpay order creation fallback: {}", e.getMessage());
            orderId = "order_mock_" + System.currentTimeMillis();
        }

        Payment payment = new Payment();
        payment.setBill(bill);
        payment.setAmount(amount);
        payment.setOrderId(orderId);
        payment.setStatus(Payment.Status.PENDING);

        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment verifyAndProcessPayment(PaymentRequest request) {
        Bill bill = billingService.getBillById(request.getBillId());

        Payment payment = paymentRepository.findByOrderId(request.getRazorpayOrderId())
                .orElseGet(() -> {
                    Payment p = new Payment();
                    p.setBill(bill);
                    p.setAmount(request.getAmount());
                    p.setOrderId(request.getRazorpayOrderId() != null ? request.getRazorpayOrderId() : "order_manual_" + System.currentTimeMillis());
                    return p;
                });

        payment.setPaymentId(request.getRazorpayPaymentId() != null ? request.getRazorpayPaymentId() : "pay_" + System.currentTimeMillis());
        payment.setRazorpaySignature(request.getRazorpaySignature());
        payment.setStatus(Payment.Status.SUCCESS);
        payment.setPaymentDate(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);

        billingService.updatePaymentStatus(bill.getId(), Bill.PaymentStatus.PAID);

        emailService.sendPaymentSuccessEmail(
                bill.getPatient().getEmail(),
                saved.getPaymentId(),
                saved.getAmount().toString()
        );

        return saved;
    }
}
