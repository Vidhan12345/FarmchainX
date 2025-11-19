package com.vidhan.FarmchainX.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for transferring product from distributor to retailer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetailerTransferRequest {

    @NotNull(message = "Retailer ID is required")
    private Long retailerId;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.01", message = "Quantity must be greater than 0")
    private BigDecimal quantity;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    private String notes;
}
