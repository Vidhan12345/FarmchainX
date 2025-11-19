package com.vidhan.FarmchainX.controller;

import com.vidhan.FarmchainX.dto.MessageResponse;
import com.vidhan.FarmchainX.dto.ProductRequest;
import com.vidhan.FarmchainX.dto.ProductResponse;
import com.vidhan.FarmchainX.dto.ProductUpdateRequest;
import com.vidhan.FarmchainX.entity.ProductStatus;
import com.vidhan.FarmchainX.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmer")
@CrossOrigin(origins = "*")
public class FarmerController {

    @Autowired
    private ProductService productService;

    /**
     * Register a new product
     * POST /api/farmer/products
     */
    @PostMapping("/products")
    public ResponseEntity<?> registerProduct(
            @Valid @RequestBody ProductRequest request,
            @RequestHeader("User-Id") Long userId) {
        try {
            ProductResponse response = productService.registerProduct(request, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Get all products for logged-in farmer
     * GET /api/farmer/products
     */
    @GetMapping("/products")
    public ResponseEntity<?> getFarmerProducts(@RequestHeader("User-Id") Long userId) {
        try {
            List<ProductResponse> products = productService.getFarmerProducts(userId);
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse());
        }
    }

    /**
     * Get product by ID
     * GET /api/farmer/products/{id}
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            ProductResponse product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse());
        }
    }

    /**
     * Full update product (requires all fields)
     * PUT /api/farmer/products/{id}
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request,
            @RequestHeader("User-Id") Long userId) {
        try {
            ProductResponse response = productService.updateProduct(id, request, userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Partial update product (only updates provided fields)
     * PATCH /api/farmer/products/{id}
     */
    @PatchMapping("/products/{id}")
    public ResponseEntity<?> partialUpdateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request,
            @RequestHeader("User-Id") Long userId) {
        try {
            ProductResponse response = productService.partialUpdateProduct(id, request, userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Update product status (e.g., mark as harvested)
     * PUT /api/farmer/products/{id}/status
     */
    @PutMapping("/products/{id}/status")
    public ResponseEntity<?> updateProductStatus(
            @PathVariable Long id,
            @RequestParam ProductStatus status,
            @RequestHeader("User-Id") Long userId) {
        try {
            ProductResponse response = productService.updateProductStatus(id, status, userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse());
        }
    }

    /**
     * Delete product (soft delete by default, hard delete with ?permanent=true)
     * DELETE /api/farmer/products/{id}
     * DELETE /api/farmer/products/{id}?permanent=true (permanent deletion)
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable Long id,
            @RequestHeader("User-Id") Long userId,
            @RequestParam(value = "permanent", defaultValue = "false") boolean permanent) {
        try {
            if (permanent) {
                productService.permanentlyDeleteProduct(id, userId);
                return ResponseEntity.ok(new MessageResponse("Product permanently deleted from database"));
            } else {
                productService.deleteProduct(id, userId);
                return ResponseEntity.ok(new MessageResponse("Product deleted successfully"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Get farmer dashboard with statistics
     * GET /api/farmer/dashboard
     */
    @GetMapping("/dashboard")
    public ResponseEntity<?> getFarmerDashboard(@RequestHeader("User-Id") Long userId) {
        try {
            ProductService.FarmerDashboardResponse dashboard = productService.getFarmerDashboard(userId);
            return ResponseEntity.ok(dashboard);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse());
        }
    }

    /**
     * Record harvest details
     * POST /api/farmer/products/{id}/harvest
     */
    @PostMapping("/products/{id}/harvest")
    public ResponseEntity<?> recordHarvest(
            @PathVariable Long id,
            @RequestHeader("User-Id") Long userId) {
        try {
            ProductResponse response = productService.updateProductStatus(id, ProductStatus.HARVESTED, userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse());
        }
    }

    /**
     * Add quality check
     * POST /api/farmer/products/{id}/quality-check
     */
    @PostMapping("/products/{id}/quality-check")
    public ResponseEntity<?> addQualityCheck(
            @PathVariable Long id,
            @RequestHeader("User-Id") Long userId) {
        try {
            ProductResponse response = productService.updateProductStatus(id, ProductStatus.QUALITY_CHECKED, userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse());
        }
    }
}
