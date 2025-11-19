package com.vidhan.FarmchainX.controller;

import com.vidhan.FarmchainX.dto.*;
import com.vidhan.FarmchainX.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Retailer operations
 * Base URL: /api/retailer
 */
@RestController
@RequestMapping("/api/retailer")
public class RetailerController {

    @Autowired
    private ProductService productService;

    /**
     * View all products available for retailers (from distributors)
     * GET /api/retailer/products/available
     */
    @GetMapping("/products/available")
    public ResponseEntity<?> getAvailableProducts() {
        try {
            List<ProductResponse> products = productService.getAvailableProductsForRetailer();
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Request product from distributor
     * POST /api/retailer/products/{id}/request
     */
    @PostMapping("/products/{id}/request")
    public ResponseEntity<?> requestProduct(
            @PathVariable Long id,
            @Valid @RequestBody TransferRequest request,
            @RequestHeader("User-Id") Long retailerId) {
        try {
            ProductResponse response = productService.requestProductFromDistributor(
                    id,
                    retailerId,
                    request.getRequestedQuantity(),
                    request.getPrice()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * View all products owned by retailer
     * GET /api/retailer/products
     */
    @GetMapping("/products")
    public ResponseEntity<?> getRetailerProducts(@RequestHeader("User-Id") Long retailerId) {
        try {
            List<ProductResponse> products = productService.getRetailerProducts(retailerId);
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Update product price by retailer
     * PATCH /api/retailer/products/{id}/price
     */
    @PatchMapping("/products/{id}/price")
    public ResponseEntity<?> updateProductPrice(
            @PathVariable Long id,
            @Valid @RequestBody PriceUpdateRequest request,
            @RequestHeader("User-Id") Long retailerId) {
        try {
            ProductResponse response = productService.updateRetailerPrice(id, retailerId, request.getPrice());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Mark product as sold
     * POST /api/retailer/products/{id}/sell
     */
    @PostMapping("/products/{id}/sell")
    public ResponseEntity<?> sellProduct(
            @PathVariable Long id,
            @Valid @RequestBody SaleRequest request,
            @RequestHeader("User-Id") Long retailerId) {
        try {
            ProductResponse response = productService.markProductAsSold(
                    id,
                    retailerId,
                    request.getSoldQuantity(),
                    request.getFinalPrice(),
                    request.getCustomerId()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Get retailer dashboard with statistics
     * GET /api/retailer/dashboard
     */
    @GetMapping("/dashboard")
    public ResponseEntity<?> getRetailerDashboard(@RequestHeader("User-Id") Long retailerId) {
        try {
            ProductService.RetailerDashboardResponse dashboard = productService.getRetailerDashboard(retailerId);
            return ResponseEntity.ok(dashboard);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
