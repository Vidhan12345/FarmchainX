package com.vidhan.FarmchainX.controller;

import com.vidhan.FarmchainX.dto.*;
import com.vidhan.FarmchainX.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Distributor operations
 * Base URL: /api/distributor
 */
@RestController
@RequestMapping("/api/distributor")
public class DistributorController {

    @Autowired
    private ProductService productService;

    /**
     * View all products available for distributors
     * GET /api/distributor/products/available
     */
    @GetMapping("/products/available")
    public ResponseEntity<?> getAvailableProducts() {
        try {
            List<ProductResponse> products = productService.getAvailableProductsForDistributor();
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Request product transfer from farmer to distributor
     * POST /api/distributor/products/{id}/request
     */
    @PostMapping("/products/{id}/request")
    public ResponseEntity<?> requestProductTransfer(
            @PathVariable Long id,
            @Valid @RequestBody TransferRequest request,
            @RequestHeader("User-Id") Long distributorId) {
        try {
            ProductResponse response = productService.requestProductTransfer(
                    id, 
                    distributorId, 
                    request.getRequestedQuantity(), 
                    request.getPrice()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * View all products owned by distributor
     * GET /api/distributor/products
     */
    @GetMapping("/products")
    public ResponseEntity<?> getDistributorProducts(@RequestHeader("User-Id") Long distributorId) {
        try {
            List<ProductResponse> products = productService.getDistributorProducts(distributorId);
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Update product price by distributor
     * PATCH /api/distributor/products/{id}/price
     */
    @PatchMapping("/products/{id}/price")
    public ResponseEntity<?> updateProductPrice(
            @PathVariable Long id,
            @Valid @RequestBody PriceUpdateRequest request,
            @RequestHeader("User-Id") Long distributorId) {
        try {
            ProductResponse response = productService.updateDistributorPrice(id, distributorId, request.getPrice());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Transfer product from distributor to retailer
     * POST /api/distributor/products/{id}/transfer-to-retailer
     */
    @PostMapping("/products/{id}/transfer-to-retailer")
    public ResponseEntity<?> transferToRetailer(
            @PathVariable Long id,
            @Valid @RequestBody RetailerTransferRequest request,
            @RequestHeader("User-Id") Long distributorId) {
        try {
            ProductResponse response = productService.transferToRetailer(
                    id,
                    distributorId,
                    request.getRetailerId(),
                    request.getQuantity(),
                    request.getPrice()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Get distributor dashboard with statistics
     * GET /api/distributor/dashboard
     */
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDistributorDashboard(@RequestHeader("User-Id") Long distributorId) {
        try {
            ProductService.DistributorDashboardResponse dashboard = productService.getDistributorDashboard(distributorId);
            return ResponseEntity.ok(dashboard);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
