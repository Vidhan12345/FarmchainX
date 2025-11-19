package com.vidhan.FarmchainX.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for system-wide statistics
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemStatsResponse {
    // User statistics
    private Long totalUsers;
    private Long activeFarmers;
    private Long activeDistributors;
    private Long activeRetailers;
    private Long activeConsumers;
    
    // Product statistics
    private Long totalProducts;
    private Long activeProducts;
    private Long registeredProducts;
    private Long harvestedProducts;
    private Long qualityCheckedProducts;
    private Long productsWithFarmers;
    private Long productsWithDistributors;
    private Long productsWithRetailers;
    private Long soldProducts;
    
    // Financial statistics
    private BigDecimal totalRevenue;
    private BigDecimal averageProductPrice;
    private BigDecimal totalFarmerRevenue;
    private BigDecimal totalDistributorRevenue;
    private BigDecimal totalRetailerRevenue;
    
    // Supply chain metrics
    private Double averageSupplyChainDays;
    private Long totalTransfers;
    private Double averagePriceMarkup;
}
