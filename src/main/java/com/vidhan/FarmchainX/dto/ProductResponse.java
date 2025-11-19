package com.vidhan.FarmchainX.dto;

import com.vidhan.FarmchainX.entity.FarmingPractice;
import com.vidhan.FarmchainX.entity.ProductCategory;
import com.vidhan.FarmchainX.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private ProductCategory category;
    private String variety;
    private String description;
    
    // Farmer info
    private Long farmerId;
    private String farmerName;
    private String farmerEmail;
    
    // Harvest details
    private LocalDate harvestDate;
    private BigDecimal quantity;
    private String unit;
    
    // Location
    private String farmLocation;
    private String farmAddress;
    private String city;
    private String state;
    private String pincode;
    private Double latitude;
    private Double longitude;
    
    // Farming details
    private FarmingPractice farmingPractice;
    private String certifications;
    
    // Quality
    private String qualityGrade;
    private String size;
    private String qualityNotes;
    
    // Status
    private ProductStatus status;
    private String batchNumber;
    private String qrCode;
    
    // Pricing
    private BigDecimal farmerPrice;
    private BigDecimal currentPrice;
    
    // Current owner
    private Long currentOwnerId;
    private String currentOwnerName;
    
    // Images
    private String imageUrl;
    private String certificateUrl;
    
    // Metadata
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
