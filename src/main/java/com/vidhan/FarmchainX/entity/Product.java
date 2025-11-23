package com.vidhan.FarmchainX.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String batchId; // Auto-generated: BATCH-YYYYMMDD-RANDOM

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;

    // Basic Product Info
    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String cropType;

    private String seedType;
    private String soilType;
    private String irrigation;
    private String fertilizers;
    private String pesticides;

    @Column(nullable = false)
    private Integer quantity; // in kg or units

    private String quality; // A+, A, B, C

    // Dates
    @Column(nullable = false)
    private LocalDate cultivationStart;

    @Column(nullable = false)
    private LocalDate cultivationEnd;

    @Column(nullable = false)
    private LocalDate harvestDate;

    // Storage Info
    private Integer shelfLife; // in days
    private Integer expiryDays; // auto-calculated
    private String storage; // "Cold Storage", "Room Temperature"
    private Double storageTemp; // in Celsius

    // Images
    private String image; // Primary image URL

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>(); // Multiple image URLs

    // Metadata (CRITICAL: Frontend expects createdOn/updatedOn, NOT
    // createdAt/updatedAt)
    @CreationTimestamp
    @Column(name = "created_on", updatable = false, nullable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on", nullable = false)
    private LocalDateTime updatedOn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status = ProductStatus.CULTIVATION;

    private Boolean qrVerified = false;

    // Additional fields
    @Column(length = 2000)
    private String description;

    private Double price; // Price per unit
    private String unit; // "kg", "liters", "pieces"
    private Boolean organic = false;
    private String certifications;
}
