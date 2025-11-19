package com.vidhan.FarmchainX.dto;

import com.vidhan.FarmchainX.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for admin product audit operations
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAuditRequest {
    private ProductStatus newStatus;
    
    @NotBlank(message = "Audit notes are required")
    private String auditNotes;
    
    private Boolean approveProduct;
    private Boolean flagForReview;
}
