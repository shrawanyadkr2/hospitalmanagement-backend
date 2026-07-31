package com.shrawan.hospitalmanagement.controller;

import com.shrawan.hospitalmanagement.dto.ApiResponse;
import com.shrawan.hospitalmanagement.entity.MedicalRecord;
import com.shrawan.hospitalmanagement.service.MedicalRecordService;
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
@RequestMapping(AppConstants.MEDICAL_RECORD)
@Tag(name = "Medical Record Module", description = "Patient medical records management (Diagnosis, Tests, Reports, Notes)")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    @Operation(summary = "Create medical record (Doctor/Admin)")
    public ResponseEntity<ApiResponse<MedicalRecord>> createMedicalRecord(
            @RequestParam Long doctorId,
            @RequestParam Long patientId,
            @Valid @RequestBody MedicalRecord record
    ) {
        MedicalRecord created = medicalRecordService.createMedicalRecord(doctorId, patientId, record);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Medical record created successfully", created));
    }

    @GetMapping
    @Operation(summary = "Get all medical records with pagination")
    public ResponseEntity<ApiResponse<Page<MedicalRecord>>> getAllMedicalRecords(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MedicalRecord> records = medicalRecordService.getAllMedicalRecords(pageable);
        return ResponseEntity.ok(ApiResponse.success("Medical records retrieved successfully", records));
    }

    @GetMapping(AppConstants.ID)
    @Operation(summary = "Get medical record by ID")
    public ResponseEntity<ApiResponse<MedicalRecord>> getRecordById(@PathVariable Long id) {
        MedicalRecord record = medicalRecordService.getRecordById(id);
        return ResponseEntity.ok(ApiResponse.success("Medical record retrieved successfully", record));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get all medical records for a patient")
    public ResponseEntity<ApiResponse<List<MedicalRecord>>> getRecordsByPatient(@PathVariable Long patientId) {
        List<MedicalRecord> list = medicalRecordService.getRecordsByPatient(patientId);
        return ResponseEntity.ok(ApiResponse.success("Patient medical records retrieved successfully", list));
    }

    @GetMapping("/doctor/{doctorId}")
    @Operation(summary = "Get all medical records created by a doctor")
    public ResponseEntity<ApiResponse<List<MedicalRecord>>> getRecordsByDoctor(@PathVariable Long doctorId) {
        List<MedicalRecord> list = medicalRecordService.getRecordsByDoctor(doctorId);
        return ResponseEntity.ok(ApiResponse.success("Doctor medical records retrieved successfully", list));
    }

    @PutMapping(AppConstants.ID)
    @Operation(summary = "Update medical record (Doctor/Admin)")
    public ResponseEntity<ApiResponse<MedicalRecord>> updateMedicalRecord(
            @PathVariable Long id,
            @Valid @RequestBody MedicalRecord record
    ) {
        MedicalRecord updated = medicalRecordService.updateMedicalRecord(id, record);
        return ResponseEntity.ok(ApiResponse.success("Medical record updated successfully", updated));
    }

    @DeleteMapping(AppConstants.ID)
    @Operation(summary = "Delete medical record")
    public ResponseEntity<ApiResponse<Void>> deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.ok(ApiResponse.success("Medical record deleted successfully"));
    }
}
