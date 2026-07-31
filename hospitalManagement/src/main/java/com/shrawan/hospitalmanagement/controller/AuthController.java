package com.shrawan.hospitalmanagement.controller;

import com.shrawan.hospitalmanagement.dto.ApiResponse;
import com.shrawan.hospitalmanagement.dto.JwtResponse;
import com.shrawan.hospitalmanagement.dto.LoginRequest;
import com.shrawan.hospitalmanagement.dto.RegisterRequest;
import com.shrawan.hospitalmanagement.entity.User;
import com.shrawan.hospitalmanagement.service.AuthService;
import com.shrawan.hospitalmanagement.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(AppConstants.AUTH)
@Tag(name = "Authentication Module", description = "User registration, login, JWT token, verification and password flows")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(AppConstants.REGISTER)
    @Operation(summary = "Register new user (Patient, Doctor, Receptionist, Admin)")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully. Please verify your email.", user));
    }

    @PostMapping(AppConstants.LOGIN)
    @Operation(summary = "Authenticate user and get JWT token")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginRequest request) {
        JwtResponse jwtResponse = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", jwtResponse));
    }

    @GetMapping(AppConstants.VERIFY_EMAIL)
    @Operation(summary = "Verify user email address using token")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@RequestParam("token") String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok(ApiResponse.success("Email verified successfully"));
    }

    @PostMapping(AppConstants.FORGOT_PASSWORD)
    @Operation(summary = "Send password reset token via email")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestParam("email") String email) {
        authService.forgotPassword(email);
        return ResponseEntity.ok(ApiResponse.success("Password reset token sent to your email"));
    }

    @PostMapping(AppConstants.RESET_PASSWORD)
    @Operation(summary = "Reset password using token")
    public ResponseEntity<ApiResponse<String>> resetPassword(
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword
    ) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.ok(ApiResponse.success("Password reset successfully"));
    }

    @GetMapping(AppConstants.PROFILE)
    @Operation(summary = "Get current authenticated user profile")
    public ResponseEntity<ApiResponse<User>> getProfile(Principal principal) {
        User profile = authService.getProfile(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Profile fetched successfully", profile));
    }
}
