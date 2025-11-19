package com.vidhan.FarmchainX.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for recording product sale by retailer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequest {

    @NotNull(message = "Sold quantity is required")
    @DecimalMin(value = "0.01", message = "Quantity must be greater than 0")
    private BigDecimal soldQuantity;

    @NotNull(message = "Final price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal finalPrice;

    private Long customerId;
    private String customerName;
    private String notes;
}
