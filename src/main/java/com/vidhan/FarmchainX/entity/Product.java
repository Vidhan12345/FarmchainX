package com.vidhan.FarmchainX.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    private String variety;

    @Column(length = 1000)
    private String description;

    // Farmer Information
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;

    // Harvest Details
    private LocalDate harvestDate;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private String unit; // kg, tons, liters, etc.

    // Location
    private String farmLocation;
    private String farmAddress;
    private String city;
    private String state;
    private String pincode;
    
    @Column(name = "latitude")
    private Double latitude;
    
    @Column(name = "longitude")
    private Double longitude;

    // Farming Details
    @Enumerated(EnumType.STRING)
    private FarmingPractice farmingPractice;

    private String certifications; // Comma-separated certifications

    // Quality Metrics
    private String qualityGrade; // A, B, C, etc.
    private String size; // Small, Medium, Large
    
    @Column(length = 1000)
    private String qualityNotes;

    // Status & Tracking
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status = ProductStatus.REGISTERED;

    @Column(unique = true)
    private String batchNumber;

    @Column(unique = true)
    private String qrCode;

    // Pricing
    private BigDecimal farmerPrice; // Price set by farmer
    private BigDecimal currentPrice; // Current market price

    // Current Owner (for relationship)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_owner_id", insertable = false, updatable = false)
    private User currentOwner;
    
    // Current Owner fields (for direct access)
    @Column(name = "current_owner_id")
    private Long currentOwnerId;
    
    @Column(name = "current_owner_name")
    private String currentOwnerName;

    // Images
    private String imageUrl;
    private String certificateUrl;

    // Metadata
    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
