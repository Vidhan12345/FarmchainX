package com.vidhan.FarmchainX.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for product with complete journey/traceability information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductJourneyResponse {
    // Basic product info
    private ProductResponse product;
    
    // Complete journey timeline
    private List<JourneyStep> journey;
    
    // Summary statistics
    private int totalSteps;
    private int daysInSupplyChain;
    private String currentStage;
    
    // Traceability verification
    private boolean isVerified;
    private String qrCode;
    private String batchNumber;
}
