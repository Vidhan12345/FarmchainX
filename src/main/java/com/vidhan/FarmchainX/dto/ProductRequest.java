package com.vidhan.FarmchainX.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "Crop type is required")
    private String cropType;

    private String seedType;
    private String soilType;
    private String irrigation;
    private String fertilizers;
    private String pesticides;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private String quality; // A+, A, B, C

    @NotNull(message = "Cultivation start date is required")
    private LocalDate cultivationStart;

    @NotNull(message = "Cultivation end date is required")
    private LocalDate cultivationEnd;

    @NotNull(message = "Harvest date is required")
    private LocalDate harvestDate;

    private Integer shelfLife; // in days
    private String storage; // "Cold Storage", "Room Temperature"
    private Double storageTemp; // in Celsius

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Min(value = 0, message = "Price must be positive")
    private Double price;

    private String unit; // "kg", "liters", "pieces"
    private Boolean organic = false;
    private String certifications;
}
