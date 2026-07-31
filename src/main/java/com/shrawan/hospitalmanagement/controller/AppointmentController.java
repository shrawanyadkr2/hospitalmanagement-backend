package com.shrawan.hospitalmanagement.controller;

import com.shrawan.hospitalmanagement.dto.ApiResponse;
import com.shrawan.hospitalmanagement.dto.AppointmentRequest;
import com.shrawan.hospitalmanagement.entity.Appointment;
import com.shrawan.hospitalmanagement.service.AppointmentService;
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
@RequestMapping(AppConstants.APPOINTMENT)
@Tag(name = "Appointment Module", description = "Appointment scheduling, status updates, and cancellation APIs")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @Operation(summary = "Schedule new appointment (Receptionist/Patient/Admin)")
    public ResponseEntity<ApiResponse<Appointment>> scheduleAppointment(@Valid @RequestBody AppointmentRequest request) {
        Appointment appointment = appointmentService.scheduleAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Appointment scheduled successfully", appointment));
    }

    @GetMapping
    @Operation(summary = "Get all appointments with pagination")
    public ResponseEntity<ApiResponse<Page<Appointment>>> getAllAppointments(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Appointment> appointments = appointmentService.getAllAppointments(pageable);
        return ResponseEntity.ok(ApiResponse.success("Appointments retrieved successfully", appointments));
    }

    @GetMapping(AppConstants.ID)
    @Operation(summary = "Get appointment details by ID")
    public ResponseEntity<ApiResponse<Appointment>> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(ApiResponse.success("Appointment retrieved successfully", appointment));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get appointments list for a specific patient")
    public ResponseEntity<ApiResponse<List<Appointment>>> getAppointmentsByPatient(@PathVariable Long patientId) {
        List<Appointment> list = appointmentService.getAppointmentsByPatient(patientId);
        return ResponseEntity.ok(ApiResponse.success("Patient appointments retrieved successfully", list));
    }

    @GetMapping("/doctor/{doctorId}")
    @Operation(summary = "Get appointments list assigned to a doctor")
    public ResponseEntity<ApiResponse<List<Appointment>>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        List<Appointment> list = appointmentService.getAppointmentsByDoctor(doctorId);
        return ResponseEntity.ok(ApiResponse.success("Doctor appointments retrieved successfully", list));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update appointment status (BOOKED, CONFIRMED, COMPLETED, CANCELLED)")
    public ResponseEntity<ApiResponse<Appointment>> updateStatus(
            @PathVariable Long id,
            @RequestParam Appointment.Status status
    ) {
        Appointment updated = appointmentService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Appointment status updated successfully", updated));
    }

    @PutMapping(AppConstants.ID)
    @Operation(summary = "Update appointment details")
    public ResponseEntity<ApiResponse<Appointment>> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequest request
    ) {
        Appointment updated = appointmentService.updateAppointment(id, request);
        return ResponseEntity.ok(ApiResponse.success("Appointment updated successfully", updated));
    }

    @DeleteMapping(AppConstants.ID)
    @Operation(summary = "Cancel appointment")
    public ResponseEntity<ApiResponse<Void>> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok(ApiResponse.success("Appointment cancelled successfully"));
    }
}
