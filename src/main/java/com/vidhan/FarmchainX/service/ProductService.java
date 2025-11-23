package com.vidhan.FarmchainX.service;

import com.vidhan.FarmchainX.dto.ProductRequest;
import com.vidhan.FarmchainX.entity.Product;
import com.vidhan.FarmchainX.entity.ProductStatus;
import com.vidhan.FarmchainX.entity.User;
import com.vidhan.FarmchainX.repository.ProductRepository;
import com.vidhan.FarmchainX.repository.UserRepository;
import com.vidhan.FarmchainX.util.BatchIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplyChainEventService supplyChainEventService;

    /**
     * Create a new product (Farmer)
     */
    @Transactional
    public Product createProduct(ProductRequest request, String farmerId) {
        User farmer = userRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));

        Product product = new Product();

        // Auto-generate batch ID
        product.setBatchId(BatchIdGenerator.generate());

        // Set farmer
        product.setFarmer(farmer);

        // Basic info
        product.setProductName(request.getProductName());
        product.setCropType(request.getCropType());
        product.setSeedType(request.getSeedType());
        product.setSoilType(request.getSoilType());
        product.setIrrigation(request.getIrrigation());
        product.setFertilizers(request.getFertilizers());
        product.setPesticides(request.getPesticides());

        // Quantity and quality
        product.setQuantity(request.getQuantity());
        product.setQuality(request.getQuality());

        // Dates
        product.setCultivationStart(request.getCultivationStart());
        product.setCultivationEnd(request.getCultivationEnd());
        product.setHarvestDate(request.getHarvestDate());

        // Storage
        product.setShelfLife(request.getShelfLife());
        product.setStorage(request.getStorage());
        product.setStorageTemp(request.getStorageTemp());

        // Calculate expiry days
        if (request.getHarvestDate() != null && request.getShelfLife() != null) {
            long daysSinceHarvest = ChronoUnit.DAYS.between(request.getHarvestDate(), LocalDateTime.now());
            int expiryDays = request.getShelfLife() - (int) daysSinceHarvest;
            product.setExpiryDays(expiryDays);
        }

        // Additional info
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setUnit(request.getUnit());
        product.setOrganic(request.getOrganic() != null ? request.getOrganic() : false);
        product.setCertifications(request.getCertifications());

        // Status
        product.setStatus(ProductStatus.CULTIVATION);
        product.setQrVerified(false);

        Product savedProduct = productRepository.save(product);

        // Auto-create harvest event for traceability
        try {
            supplyChainEventService.createHarvestEvent(savedProduct);

            // If product is organic, create quality check event
            if (savedProduct.getOrganic()) {
                supplyChainEventService.createQualityCheckEvent(
                        savedProduct,
                        "Quality Inspector - " + farmer.getName(),
                        "Organic Certified - Grade "
                                + (savedProduct.getQuality() != null ? savedProduct.getQuality() : "A"));
            }
        } catch (Exception e) {
            // Log but don't fail product creation if event creation fails
            System.err.println("Failed to create supply chain events: " + e.getMessage());
        }

        return savedProduct;
    }

    /**
     * Get all products by farmer
     */
    public List<Product> getProductsByFarmer(String farmerId) {
        return productRepository.findByFarmerId(farmerId);
    }

    /**
     * Get product by ID
     */
    public Product getProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    /**
     * Update product
     */
    @Transactional
    public Product updateProduct(String productId, ProductRequest request, String farmerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Verify ownership
        if (!product.getFarmer().getId().equals(farmerId)) {
            throw new RuntimeException("Not authorized to update this product");
        }

        // Update fields
        if (request.getProductName() != null)
            product.setProductName(request.getProductName());
        if (request.getCropType() != null)
            product.setCropType(request.getCropType());
        if (request.getSeedType() != null)
            product.setSeedType(request.getSeedType());
        if (request.getSoilType() != null)
            product.setSoilType(request.getSoilType());
        if (request.getIrrigation() != null)
            product.setIrrigation(request.getIrrigation());
        if (request.getFertilizers() != null)
            product.setFertilizers(request.getFertilizers());
        if (request.getPesticides() != null)
            product.setPesticides(request.getPesticides());
        if (request.getQuantity() != null)
            product.setQuantity(request.getQuantity());
        if (request.getQuality() != null)
            product.setQuality(request.getQuality());
        if (request.getCultivationStart() != null)
            product.setCultivationStart(request.getCultivationStart());
        if (request.getCultivationEnd() != null)
            product.setCultivationEnd(request.getCultivationEnd());
        if (request.getHarvestDate() != null)
            product.setHarvestDate(request.getHarvestDate());
        if (request.getShelfLife() != null)
            product.setShelfLife(request.getShelfLife());
        if (request.getStorage() != null)
            product.setStorage(request.getStorage());
        if (request.getStorageTemp() != null)
            product.setStorageTemp(request.getStorageTemp());
        if (request.getDescription() != null)
            product.setDescription(request.getDescription());
        if (request.getPrice() != null)
            product.setPrice(request.getPrice());
        if (request.getUnit() != null)
            product.setUnit(request.getUnit());
        if (request.getOrganic() != null)
            product.setOrganic(request.getOrganic());
        if (request.getCertifications() != null)
            product.setCertifications(request.getCertifications());

        // Recalculate expiry days
        if (product.getHarvestDate() != null && product.getShelfLife() != null) {
            long daysSinceHarvest = ChronoUnit.DAYS.between(product.getHarvestDate(), LocalDateTime.now());
            int expiryDays = product.getShelfLife() - (int) daysSinceHarvest;
            product.setExpiryDays(expiryDays);
        }

        return productRepository.save(product);
    }

    /**
     * Delete product
     */
    @Transactional
    public void deleteProduct(String productId, String farmerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Verify ownership
        if (!product.getFarmer().getId().equals(farmerId)) {
            throw new RuntimeException("Not authorized to delete this product");
        }

        productRepository.delete(product);
    }

    /**
     * Update product status
     */
    @Transactional
    public Product updateProductStatus(String productId, ProductStatus status, String farmerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Verify ownership
        if (!product.getFarmer().getId().equals(farmerId)) {
            throw new RuntimeException("Not authorized to update this product");
        }

        // Store previous status for event
        String previousStatus = product.getStatus().toString();

        product.setStatus(status);
        Product updatedProduct = productRepository.save(product);

        // Auto-create status change event
        try {
            supplyChainEventService.createStatusChangeEvent(
                    updatedProduct,
                    previousStatus,
                    status.toString());
        } catch (Exception e) {
            System.err.println("Failed to create status change event: " + e.getMessage());
        }

        return updatedProduct;
    }

    /**
     * Add images to product
     */
    @Transactional
    public Product addProductImages(String productId, List<String> imageUrls, String farmerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Verify ownership
        if (!product.getFarmer().getId().equals(farmerId)) {
            throw new RuntimeException("Not authorized to update this product");
        }

        // Add images
        if (product.getImages() == null) {
            product.setImages(imageUrls);
        } else {
            product.getImages().addAll(imageUrls);
        }

        // Set first image as primary if not set
        if (product.getImage() == null && !imageUrls.isEmpty()) {
            product.setImage(imageUrls.get(0));
        }

        return productRepository.save(product);
    }

    /**
     * Get all products (for consumers)
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Search products by crop type
     */
    public List<Product> searchProductsByCropType(String cropType) {
        return productRepository.findByCropTypeContainingIgnoreCase(cropType);
    }

    /**
     * Get products by status
     */
    public List<Product> getProductsByStatus(ProductStatus status) {
        return productRepository.findByStatus(status);
    }

    /**
     * Search products by name
     */
    public List<Product> searchProductsByName(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }

    /**
     * Get organic/non-organic products
     */
    public List<Product> getOrganicProducts(Boolean organic) {
        return productRepository.findByOrganic(organic);
    }
}
