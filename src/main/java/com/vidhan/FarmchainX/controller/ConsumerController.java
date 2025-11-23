package com.vidhan.FarmchainX.controller;

import com.vidhan.FarmchainX.dto.MessageResponse;
import com.vidhan.FarmchainX.entity.Product;
import com.vidhan.FarmchainX.entity.ProductStatus;
import com.vidhan.FarmchainX.entity.SupplyChainEvent;
import com.vidhan.FarmchainX.repository.SupplyChainEventRepository;
import com.vidhan.FarmchainX.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consumer")
@CrossOrigin(origins = "*")
public class ConsumerController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplyChainEventRepository supplyChainEventRepository;

    /**
     * Get all available products
     * GET /api/consumer/products
     */
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(
            @RequestParam(required = false) String cropType,
            @RequestParam(required = false) Boolean organic,
            @RequestParam(required = false) String search) {
        try {
            List<Product> products;

            if (search != null && !search.isEmpty()) {
                // Search in both product name and crop type
                List<Product> byName = productService.searchProductsByName(search);
                List<Product> byCrop = productService.searchProductsByCropType(search);

                // Combine and remove duplicates
                products = new java.util.ArrayList<>(byName);
                byCrop.forEach(p -> {
                    if (!products.stream().anyMatch(existing -> existing.getId().equals(p.getId()))) {
                        products.add(p);
                    }
                });
            } else if (cropType != null && !cropType.isEmpty()) {
                products = productService.searchProductsByCropType(cropType);
            } else if (organic != null) {
                // Use repository method for organic filter
                products = productService.getOrganicProducts(organic);
            } else {
                products = productService.getAllProducts();
            }

            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Get product by ID
     * GET /api/consumer/products/{id}
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
     * Get product journey/traceability
     * GET /api/consumer/products/{id}/journey
     */
    @GetMapping("/products/{id}/journey")
    public ResponseEntity<?> getProductJourney(@PathVariable String id) {
        try {
            Product product = productService.getProductById(id);
            List<SupplyChainEvent> events = supplyChainEventRepository
                    .findByProductIdOrderByTimestampDesc(id);

            Map<String, Object> response = new HashMap<>();
            response.put("productId", product.getId());
            response.put("batchId", product.getBatchId());
            response.put("productName", product.getProductName());
            response.put("events", events);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Product not found"));
        }
    }

    /**
     * Search products by batch ID (QR code scan)
     * GET /api/consumer/trace/{batchId}
     */
    @GetMapping("/trace/{batchId}")
    public ResponseEntity<?> traceByBatchId(@PathVariable String batchId) {
        try {
            List<SupplyChainEvent> events = supplyChainEventRepository
                    .findByProductBatchIdOrderByTimestampDesc(batchId);

            if (events.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse("No product found with this batch ID"));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("batchId", batchId);
            response.put("events", events);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
