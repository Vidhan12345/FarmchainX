package com.vidhan.FarmchainX.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Role is required")
    private String role; // "CONSUMER", "FARMER", "DISTRIBUTOR", "RETAILER", "ADMIN"

    private String phone;
    private String address;
    private String company; // For distributor/retailer
}
