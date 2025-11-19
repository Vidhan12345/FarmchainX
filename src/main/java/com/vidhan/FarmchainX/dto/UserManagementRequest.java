package com.vidhan.FarmchainX.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO for admin user management operations
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    private String password; // Optional for updates

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String phoneNumber;
    private String address;
    private Set<String> roles;
    private Boolean isActive;
}
