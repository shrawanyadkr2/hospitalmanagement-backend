package com.shrawan.hospitalmanagement.controller;

import com.shrawan.hospitalmanagement.dto.ApiResponse;
import com.shrawan.hospitalmanagement.service.FileUploadService;
import com.shrawan.hospitalmanagement.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(AppConstants.FILE)
@Tag(name = "File Upload Module", description = "Cloudinary integration for profiles, medical reports, and prescription PDFs")
public class FileController {

    private final FileUploadService fileUploadService;

    public FileController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload/profile")
    @Operation(summary = "Upload patient or doctor profile image")
    public ResponseEntity<ApiResponse<String>> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        String fileUrl = fileUploadService.uploadFile(file, "profiles");
        return ResponseEntity.ok(ApiResponse.success("Profile image uploaded successfully", fileUrl));
    }

    @PostMapping("/upload/report")
    @Operation(summary = "Upload medical test report or document")
    public ResponseEntity<ApiResponse<String>> uploadMedicalReport(@RequestParam("file") MultipartFile file) {
        String fileUrl = fileUploadService.uploadFile(file, "reports");
        return ResponseEntity.ok(ApiResponse.success("Medical report uploaded successfully", fileUrl));
    }

    @PostMapping("/upload/prescription")
    @Operation(summary = "Upload prescription PDF file")
    public ResponseEntity<ApiResponse<String>> uploadPrescriptionPdf(@RequestParam("file") MultipartFile file) {
        String fileUrl = fileUploadService.uploadFile(file, "prescriptions");
        return ResponseEntity.ok(ApiResponse.success("Prescription document uploaded successfully", fileUrl));
    }
}
