package com.vidhan.FarmchainX.dto;

import com.vidhan.FarmchainX.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO representing a single step in the product journey
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JourneyStep {
    private String stage; // FARMER, DISTRIBUTOR, RETAILER, CONSUMER
    private String actorName;
    private String actorRole;
    private ProductStatus status;
    private BigDecimal price;
    private LocalDateTime timestamp;
    private String location;
    private String notes;
}
