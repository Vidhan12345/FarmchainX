package com.vidhan.FarmchainX.dto;

import com.vidhan.FarmchainX.entity.ProductCategory;
import com.vidhan.FarmchainX.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for searching/filtering products
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequest {
    private String name;
    private ProductCategory category;
    private ProductStatus status;
    private String city;
    private String state;
    private Boolean isActive;
}
