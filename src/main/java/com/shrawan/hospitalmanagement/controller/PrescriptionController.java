package com.shrawan.hospitalmanagement.controller;

import com.shrawan.hospitalmanagement.dto.ApiResponse;
import com.shrawan.hospitalmanagement.entity.Prescription;
import com.shrawan.hospitalmanagement.service.PrescriptionService;
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
@RequestMapping(AppConstants.PRESCRIPTION)
@Tag(name = "Prescription Module", description = "Prescription management APIs (Doctor write/update, Patient view)")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    @Operation(summary = "Create prescription for patient (Doctor/Admin)")
    public ResponseEntity<ApiResponse<Prescription>> createPrescription(
            @RequestParam Long doctorId,
            @RequestParam Long patientId,
            @RequestParam(required = false) Long appointmentId,
            @Valid @RequestBody Prescription prescription
    ) {
        Prescription created = prescriptionService.createPrescription(doctorId, patientId, appointmentId, prescription);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Prescription created successfully", created));
    }

    @GetMapping
    @Operation(summary = "Get all prescriptions with pagination")
    public ResponseEntity<ApiResponse<Page<Prescription>>> getAllPrescriptions(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Prescription> prescriptions = prescriptionService.getAllPrescriptions(pageable);
        return ResponseEntity.ok(ApiResponse.success("Prescriptions retrieved successfully", prescriptions));
    }

    @GetMapping(AppConstants.ID)
    @Operation(summary = "Get prescription by ID")
    public ResponseEntity<ApiResponse<Prescription>> getPrescriptionById(@PathVariable Long id) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        return ResponseEntity.ok(ApiResponse.success("Prescription retrieved successfully", prescription));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get all prescriptions for a patient")
    public ResponseEntity<ApiResponse<List<Prescription>>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        List<Prescription> list = prescriptionService.getPrescriptionsByPatient(patientId);
        return ResponseEntity.ok(ApiResponse.success("Patient prescriptions retrieved successfully", list));
    }

    @GetMapping("/doctor/{doctorId}")
    @Operation(summary = "Get all prescriptions written by a doctor")
    public ResponseEntity<ApiResponse<List<Prescription>>> getPrescriptionsByDoctor(@PathVariable Long doctorId) {
        List<Prescription> list = prescriptionService.getPrescriptionsByDoctor(doctorId);
        return ResponseEntity.ok(ApiResponse.success("Doctor prescriptions retrieved successfully", list));
    }

    @PutMapping(AppConstants.ID)
    @Operation(summary = "Update prescription (Doctor/Admin)")
    public ResponseEntity<ApiResponse<Prescription>> updatePrescription(
            @PathVariable Long id,
            @Valid @RequestBody Prescription prescription
    ) {
        Prescription updated = prescriptionService.updatePrescription(id, prescription);
        return ResponseEntity.ok(ApiResponse.success("Prescription updated successfully", updated));
    }

    @DeleteMapping(AppConstants.ID)
    @Operation(summary = "Delete prescription")
    public ResponseEntity<ApiResponse<Void>> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.ok(ApiResponse.success("Prescription deleted successfully"));
    }
}
