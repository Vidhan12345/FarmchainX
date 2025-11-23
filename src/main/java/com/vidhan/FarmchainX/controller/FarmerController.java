package com.vidhan.FarmchainX.controller;

import com.vidhan.FarmchainX.dto.MessageResponse;
import com.vidhan.FarmchainX.dto.ProductRequest;
import com.vidhan.FarmchainX.entity.Product;
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
     * Create a new product
     * POST /api/farmer/products
     */
    @PostMapping("/products")
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductRequest request,
            @RequestHeader("User-Id") String userId) {
        try {
            Product product = productService.createProduct(request, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Get all products for logged-in farmer
     * GET /api/farmer/products
     */
    @GetMapping("/products")
    public ResponseEntity<?> getFarmerProducts(@RequestHeader("User-Id") String userId) {
        try {
            List<Product> products = productService.getProductsByFarmer(userId);
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Get product by ID
     * GET /api/farmer/products/{id}
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Product not found"));
        }
    }

    /**
     * Update product
     * PUT /api/farmer/products/{id}
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody ProductRequest request,
            @RequestHeader("User-Id") String userId) {
        try {
            Product product = productService.updateProduct(id, request, userId);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Update product status
     * PUT /api/farmer/products/{id}/status
     */
    @PutMapping("/products/{id}/status")
    public ResponseEntity<?> updateProductStatus(
            @PathVariable String id,
            @RequestBody StatusUpdateRequest request,
            @RequestHeader("User-Id") String userId) {
        try {
            ProductStatus status = ProductStatus.valueOf(request.getStatus().toUpperCase());
            Product product = productService.updateProductStatus(id, status, userId);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid status: " + request.getStatus()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Delete product
     * DELETE /api/farmer/products/{id}
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable String id,
            @RequestHeader("User-Id") String userId) {
        try {
            productService.deleteProduct(id, userId);
            return ResponseEntity.ok(new MessageResponse("Product deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Add images to product
     * POST /api/farmer/products/{id}/images
     */
    @PostMapping("/products/{id}/images")
    public ResponseEntity<?> addProductImages(
            @PathVariable String id,
            @RequestBody ImageUploadRequest request,
            @RequestHeader("User-Id") String userId) {
        try {
            Product product = productService.addProductImages(id, request.getImages(), userId);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    // Helper DTOs
    public static class StatusUpdateRequest {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class ImageUploadRequest {
        private List<String> images;

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
