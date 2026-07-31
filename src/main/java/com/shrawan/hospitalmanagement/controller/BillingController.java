package com.shrawan.hospitalmanagement.controller;

import com.shrawan.hospitalmanagement.dto.ApiResponse;
import com.shrawan.hospitalmanagement.dto.BillRequest;
import com.shrawan.hospitalmanagement.entity.Bill;
import com.shrawan.hospitalmanagement.service.BillingService;
import com.shrawan.hospitalmanagement.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstants.BILL)
@Tag(name = "Billing Module", description = "Automated bill generation and status management APIs")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping
    @Operation(summary = "Generate hospital bill (Receptionist/Admin)")
    public ResponseEntity<ApiResponse<Bill>> generateBill(@Valid @RequestBody BillRequest request) {
        Bill bill = billingService.generateBill(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Bill generated successfully", bill));
    }

    @GetMapping
    @Operation(summary = "Get all hospital bills with pagination")
    public ResponseEntity<ApiResponse<Page<Bill>>> getAllBills(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Bill> bills = billingService.getAllBills(pageable);
        return ResponseEntity.ok(ApiResponse.success("Bills retrieved successfully", bills));
    }

    @GetMapping(AppConstants.ID)
    @Operation(summary = "Get bill details by ID")
    public ResponseEntity<ApiResponse<Bill>> getBillById(@PathVariable Long id) {
        Bill bill = billingService.getBillById(id);
        return ResponseEntity.ok(ApiResponse.success("Bill retrieved successfully", bill));
    }

    @GetMapping("/number/{billNumber}")
    @Operation(summary = "Get bill details by unique Bill Number")
    public ResponseEntity<ApiResponse<Bill>> getBillByNumber(@PathVariable String billNumber) {
        Bill bill = billingService.getBillByNumber(billNumber);
        return ResponseEntity.ok(ApiResponse.success("Bill retrieved successfully", bill));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get all bills for a patient")
    public ResponseEntity<ApiResponse<List<Bill>>> getBillsByPatient(@PathVariable Long patientId) {
        List<Bill> list = billingService.getBillsByPatient(patientId);
        return ResponseEntity.ok(ApiResponse.success("Patient bills retrieved successfully", list));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update bill payment status (PENDING, PAID, CANCELLED)")
    public ResponseEntity<ApiResponse<Bill>> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam Bill.PaymentStatus status
    ) {
        Bill updated = billingService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Bill payment status updated successfully", updated));
    }
}
