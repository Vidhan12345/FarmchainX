package com.vidhan.FarmchainX.controller;

import com.vidhan.FarmchainX.dto.MessageResponse;
import com.vidhan.FarmchainX.dto.ProductJourneyResponse;
import com.vidhan.FarmchainX.dto.ProductResponse;
import com.vidhan.FarmchainX.entity.ProductCategory;
import com.vidhan.FarmchainX.entity.ProductStatus;
import com.vidhan.FarmchainX.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Consumer operations
 * Base URL: /api/consumer
 * Public endpoints - no authentication required
 */
@RestController
@RequestMapping("/api/consumer")
@CrossOrigin(origins = "*")
public class ConsumerController {

    @Autowired
    private ProductService productService;

    /**
     * View all products
     * GET /api/consumer/products
     */
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<ProductResponse> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Get product details with complete journey/traceability
     * GET /api/consumer/products/{id}
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductWithJourney(@PathVariable Long id) {
        try {
            ProductJourneyResponse journey = productService.getProductWithJourney(id);
            return ResponseEntity.ok(journey);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Get product by QR code
     * GET /api/consumer/products/qr/{qrCode}
     */
    @GetMapping("/products/qr/{qrCode}")
    public ResponseEntity<?> getProductByQrCode(@PathVariable String qrCode) {
        try {
            ProductJourneyResponse journey = productService.getProductByQrCode(qrCode);
            return ResponseEntity.ok(journey);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Search products with filters
     * GET /api/consumer/products/search?name=tomato&category=VEGETABLES&city=Mumbai
     */
    @GetMapping("/products/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state) {
        try {
            List<ProductResponse> products = productService.searchProducts(name, category, status, city, state);
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
