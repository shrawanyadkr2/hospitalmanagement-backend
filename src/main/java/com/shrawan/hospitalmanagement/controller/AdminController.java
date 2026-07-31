package com.shrawan.hospitalmanagement.controller;

import com.shrawan.hospitalmanagement.dto.ApiResponse;
import com.shrawan.hospitalmanagement.entity.User;
import com.shrawan.hospitalmanagement.exception.ResourceNotFoundException;
import com.shrawan.hospitalmanagement.repository.UserRepository;
import com.shrawan.hospitalmanagement.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstants.ADMIN)
@Tag(name = "Admin Module", description = "System Administration, user status management, and audit APIs")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    @Operation(summary = "List all system users (Admin only)")
    public ResponseEntity<ApiResponse<Page<User>>> getAllUsers(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> users = userRepository.findAll(pageable);
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
    }

    @PatchMapping("/users/{id}/toggle-status")
    @Operation(summary = "Enable or disable user account (Admin only)")
    public ResponseEntity<ApiResponse<User>> toggleUserStatus(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setEnabled(!user.getEnabled());
        User updated = userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.success("User account status updated", updated));
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete user account (Admin only)")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
    }
}
