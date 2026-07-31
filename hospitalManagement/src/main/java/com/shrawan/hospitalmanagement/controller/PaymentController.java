package com.shrawan.hospitalmanagement.controller;

import com.shrawan.hospitalmanagement.dto.ApiResponse;
import com.shrawan.hospitalmanagement.dto.PaymentRequest;
import com.shrawan.hospitalmanagement.entity.Payment;
import com.shrawan.hospitalmanagement.service.PaymentService;
import com.shrawan.hospitalmanagement.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(AppConstants.PAYMENT)
@Tag(name = "Payment Module", description = "Razorpay payment integration endpoints")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order")
    @Operation(summary = "Create Razorpay Order ID for a bill")
    public ResponseEntity<ApiResponse<Payment>> createOrder(
            @RequestParam Long billId,
            @RequestParam BigDecimal amount
    ) {
        Payment payment = paymentService.createRazorpayOrder(billId, amount);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Razorpay order created successfully", payment));
    }

    @PostMapping("/verify")
    @Operation(summary = "Verify Razorpay payment signature and mark bill as paid")
    public ResponseEntity<ApiResponse<Payment>> verifyPayment(@Valid @RequestBody PaymentRequest request) {
        Payment payment = paymentService.verifyAndProcessPayment(request);
        return ResponseEntity.ok(ApiResponse.success("Payment verified and recorded successfully", payment));
    }
}
