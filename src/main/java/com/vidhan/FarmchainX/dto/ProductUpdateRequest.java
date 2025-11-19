package com.vidhan.FarmchainX.dto;

import com.vidhan.FarmchainX.entity.FarmingPractice;
import com.vidhan.FarmchainX.entity.ProductCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for updating products - all fields are optional
 * Only provided fields will be updated
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
    
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String name;

    private ProductCategory category;
    private String variety;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    private LocalDate harvestDate;

    @DecimalMin(value = "0.01", message = "Quantity must be greater than 0")
    private BigDecimal quantity;

    private String unit;

    private String farmLocation;
    private String farmAddress;
    private String city;
    private String state;
    private String pincode;
    private Double latitude;
    private Double longitude;

    private FarmingPractice farmingPractice;
    private String certifications;

    private String qualityGrade;
    private String size;
    private String qualityNotes;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal farmerPrice;

    private String imageUrl;
    private String certificateUrl;
}
