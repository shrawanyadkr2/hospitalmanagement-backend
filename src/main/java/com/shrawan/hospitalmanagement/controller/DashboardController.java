package com.shrawan.hospitalmanagement.controller;

import com.shrawan.hospitalmanagement.dto.ApiResponse;
import com.shrawan.hospitalmanagement.dto.DashboardResponse;
import com.shrawan.hospitalmanagement.service.DashboardService;
import com.shrawan.hospitalmanagement.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstants.DASHBOARD)
@Tag(name = "Dashboard Module", description = "Real-time metrics, system stats, revenue, and counts")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @Operation(summary = "Get hospital system statistics and dashboard metrics")
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboardStats() {
        DashboardResponse stats = dashboardService.getDashboardStatistics();
        return ResponseEntity.ok(ApiResponse.success("Dashboard statistics fetched successfully", stats));
    }
}
