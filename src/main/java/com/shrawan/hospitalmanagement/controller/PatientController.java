package com.shrawan.hospitalmanagement.controller;

import com.shrawan.hospitalmanagement.dto.ApiResponse;
import com.shrawan.hospitalmanagement.dto.PatientRequest;
import com.shrawan.hospitalmanagement.dto.PatientResponse;
import com.shrawan.hospitalmanagement.service.PatientService;
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

@RestController
@RequestMapping(AppConstants.PATIENT)
@Tag(name = "Patient Module", description = "Patient CRUD, search, and pagination APIs")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @Operation(summary = "Create new patient record (Receptionist/Admin)")
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(@Valid @RequestBody PatientRequest request) {
        PatientResponse response = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Patient created successfully", response));
    }

    @GetMapping
    @Operation(summary = "Get all patients with pagination and sorting")
    public ResponseEntity<ApiResponse<Page<PatientResponse>>> getAllPatients(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PatientResponse> patients = patientService.getAllPatients(pageable);
        return ResponseEntity.ok(ApiResponse.success("Patients retrieved successfully", patients));
    }

    @GetMapping(AppConstants.ID)
    @Operation(summary = "Get patient details by DB ID")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatientById(@PathVariable Long id) {
        PatientResponse patient = patientService.getPatientById(id);
        return ResponseEntity.ok(ApiResponse.success("Patient retrieved successfully", patient));
    }

    @GetMapping("/code/{patientId}")
    @Operation(summary = "Get patient details by generated Patient Code (PAT-XXXXXX)")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatientByCode(@PathVariable String patientId) {
        PatientResponse patient = patientService.getPatientByPatientId(patientId);
        return ResponseEntity.ok(ApiResponse.success("Patient retrieved successfully", patient));
    }

    @PutMapping(AppConstants.ID)
    @Operation(summary = "Update patient record")
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequest request
    ) {
        PatientResponse updated = patientService.updatePatient(id, request);
        return ResponseEntity.ok(ApiResponse.success("Patient updated successfully", updated));
    }

    @DeleteMapping(AppConstants.ID)
    @Operation(summary = "Delete patient record")
    public ResponseEntity<ApiResponse<Void>> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(ApiResponse.success("Patient deleted successfully"));
    }
}
