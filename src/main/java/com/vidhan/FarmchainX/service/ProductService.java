package com.vidhan.FarmchainX.service;

import com.vidhan.FarmchainX.dto.*;
import com.vidhan.FarmchainX.entity.*;
import com.vidhan.FarmchainX.repository.ProductRepository;
import com.vidhan.FarmchainX.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Register a new product (Farmer only)
     */
    @Transactional
    public ProductResponse registerProduct(ProductRequest request, Long farmerId) {
        User farmer = userRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));

        // Verify user is a farmer
        boolean isFarmer = farmer.getRoles().stream()
                .anyMatch(role -> role.getRoleName() == ERole.ROLE_FARMER);
        
        if (!isFarmer) {
            throw new RuntimeException("Only farmers can register products");
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setVariety(request.getVariety());
        product.setDescription(request.getDescription());
        product.setFarmer(farmer);
        product.setHarvestDate(request.getHarvestDate());
        product.setQuantity(request.getQuantity());
        product.setUnit(request.getUnit());
        product.setFarmLocation(request.getFarmLocation());
        product.setFarmAddress(request.getFarmAddress());
        product.setCity(request.getCity());
        product.setState(request.getState());
        product.setPincode(request.getPincode());
        product.setLatitude(request.getLatitude());
        product.setLongitude(request.getLongitude());
        product.setFarmingPractice(request.getFarmingPractice());
        product.setCertifications(request.getCertifications());
        product.setQualityGrade(request.getQualityGrade());
        product.setSize(request.getSize());
        product.setQualityNotes(request.getQualityNotes());
        product.setFarmerPrice(request.getFarmerPrice());
        product.setCurrentPrice(request.getFarmerPrice());
        product.setImageUrl(request.getImageUrl());
        product.setCertificateUrl(request.getCertificateUrl());
        
        // Set initial status and owner
        product.setStatus(ProductStatus.REGISTERED);
        product.setCurrentOwner(farmer);
        product.setIsActive(true);

        // Generate unique batch number and QR code
        product.setBatchNumber(generateBatchNumber());
        product.setQrCode(generateQRCode());

        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }

    /**
     * Get all products for a farmer
     */
    public List<ProductResponse> getFarmerProducts(Long farmerId) {
        User farmer = userRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));

        List<Product> products = productRepository.findByFarmerAndIsActive(farmer, true);
        return products.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get product by ID
     */
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToResponse(product);
    }

    /**
     * Update product (Farmer only - can only update own products)
     */
    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest request, Long farmerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Verify ownership
        if (!product.getFarmer().getId().equals(farmerId)) {
            throw new RuntimeException("You can only update your own products");
        }

        // Update fields
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setVariety(request.getVariety());
        product.setDescription(request.getDescription());
        product.setHarvestDate(request.getHarvestDate());
        product.setQuantity(request.getQuantity());
        product.setUnit(request.getUnit());
        product.setFarmLocation(request.getFarmLocation());
        product.setFarmAddress(request.getFarmAddress());
        product.setCity(request.getCity());
        product.setState(request.getState());
        product.setPincode(request.getPincode());
        product.setLatitude(request.getLatitude());
        product.setLongitude(request.getLongitude());
        product.setFarmingPractice(request.getFarmingPractice());
        product.setCertifications(request.getCertifications());
        product.setQualityGrade(request.getQualityGrade());
        product.setSize(request.getSize());
        product.setQualityNotes(request.getQualityNotes());
        product.setFarmerPrice(request.getFarmerPrice());
        product.setCurrentPrice(request.getFarmerPrice());
        product.setImageUrl(request.getImageUrl());
        product.setCertificateUrl(request.getCertificateUrl());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    /**
     * Partial update product - only updates provided fields
     */
    @Transactional
    public ProductResponse partialUpdateProduct(Long productId, ProductUpdateRequest request, Long farmerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Verify ownership
        if (!product.getFarmer().getId().equals(farmerId)) {
            throw new RuntimeException("You can only update your own products");
        }

        // Update only non-null fields
        if (request.getName() != null) product.setName(request.getName());
        if (request.getCategory() != null) product.setCategory(request.getCategory());
        if (request.getVariety() != null) product.setVariety(request.getVariety());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getHarvestDate() != null) product.setHarvestDate(request.getHarvestDate());
        if (request.getQuantity() != null) product.setQuantity(request.getQuantity());
        if (request.getUnit() != null) product.setUnit(request.getUnit());
        if (request.getFarmLocation() != null) product.setFarmLocation(request.getFarmLocation());
        if (request.getFarmAddress() != null) product.setFarmAddress(request.getFarmAddress());
        if (request.getCity() != null) product.setCity(request.getCity());
        if (request.getState() != null) product.setState(request.getState());
        if (request.getPincode() != null) product.setPincode(request.getPincode());
        if (request.getLatitude() != null) product.setLatitude(request.getLatitude());
        if (request.getLongitude() != null) product.setLongitude(request.getLongitude());
        if (request.getFarmingPractice() != null) product.setFarmingPractice(request.getFarmingPractice());
        if (request.getCertifications() != null) product.setCertifications(request.getCertifications());
        if (request.getQualityGrade() != null) product.setQualityGrade(request.getQualityGrade());
        if (request.getSize() != null) product.setSize(request.getSize());
        if (request.getQualityNotes() != null) product.setQualityNotes(request.getQualityNotes());
        if (request.getFarmerPrice() != null) {
            product.setFarmerPrice(request.getFarmerPrice());
            product.setCurrentPrice(request.getFarmerPrice());
        }
        if (request.getImageUrl() != null) product.setImageUrl(request.getImageUrl());
        if (request.getCertificateUrl() != null) product.setCertificateUrl(request.getCertificateUrl());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    /**
     * Update product status
     */
    @Transactional
    public ProductResponse updateProductStatus(Long productId, ProductStatus newStatus, Long userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Verify ownership
        if (!product.getCurrentOwner().getId().equals(userId)) {
            throw new RuntimeException("Only the current owner can update product status");
        }

        product.setStatus(newStatus);
        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    /**
     * Delete product (soft delete)
     */
    @Transactional
    public void deleteProduct(Long productId, Long farmerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Verify ownership
        if (!product.getFarmer().getId().equals(farmerId)) {
            throw new RuntimeException("You can only delete your own products");
        }

        product.setIsActive(false);
        productRepository.save(product);
    }

    /**
     * Permanently delete product from database (hard delete)
     */
    @Transactional
    public void permanentlyDeleteProduct(Long productId, Long farmerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Verify ownership
        if (!product.getFarmer().getId().equals(farmerId)) {
            throw new RuntimeException("You can only delete your own products");
        }

        // Log before deletion
        System.out.println("🗑️ PERMANENTLY DELETING Product ID: " + productId + " Name: " + product.getName());
        
        // Permanently delete from database
        productRepository.delete(product);
        productRepository.flush(); // Force immediate deletion
        
        System.out.println("✅ Product permanently deleted from database");
    }

    /**
     * Get farmer dashboard statistics
     */
    public FarmerDashboardResponse getFarmerDashboard(Long farmerId) {
        User farmer = userRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));

        List<Product> allProducts = productRepository.findByFarmer(farmer);
        
        long totalProducts = allProducts.size();
        long activeProducts = allProducts.stream().filter(Product::getIsActive).count();
        long soldProducts = allProducts.stream()
                .filter(p -> p.getStatus() == ProductStatus.SOLD)
                .count();

        FarmerDashboardResponse dashboard = new FarmerDashboardResponse();
        dashboard.setTotalProducts(totalProducts);
        dashboard.setActiveProducts(activeProducts);
        dashboard.setSoldProducts(soldProducts);
        dashboard.setRecentProducts(
            allProducts.stream()
                .filter(Product::getIsActive)
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .limit(5)
                .map(this::mapToResponse)
                .collect(Collectors.toList())
        );

        return dashboard;
    }

    /**
     * Helper: Map Product entity to ProductResponse DTO
     */
    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setCategory(product.getCategory());
        response.setVariety(product.getVariety());
        response.setDescription(product.getDescription());
        
        // Farmer info
        response.setFarmerId(product.getFarmer().getId());
        response.setFarmerName(product.getFarmer().getUsername());
        response.setFarmerEmail(product.getFarmer().getEmail());
        
        // Harvest details
        response.setHarvestDate(product.getHarvestDate());
        response.setQuantity(product.getQuantity());
        response.setUnit(product.getUnit());
        
        // Location
        response.setFarmLocation(product.getFarmLocation());
        response.setFarmAddress(product.getFarmAddress());
        response.setCity(product.getCity());
        response.setState(product.getState());
        response.setPincode(product.getPincode());
        response.setLatitude(product.getLatitude());
        response.setLongitude(product.getLongitude());
        
        // Farming details
        response.setFarmingPractice(product.getFarmingPractice());
        response.setCertifications(product.getCertifications());
        
        // Quality
        response.setQualityGrade(product.getQualityGrade());
        response.setSize(product.getSize());
        response.setQualityNotes(product.getQualityNotes());
        
        // Status
        response.setStatus(product.getStatus());
        response.setBatchNumber(product.getBatchNumber());
        response.setQrCode(product.getQrCode());
        
        // Pricing
        response.setFarmerPrice(product.getFarmerPrice());
        response.setCurrentPrice(product.getCurrentPrice());
        
        // Current owner
        response.setCurrentOwnerId(product.getCurrentOwnerId());
        response.setCurrentOwnerName(product.getCurrentOwnerName());
        
        // Images
        response.setImageUrl(product.getImageUrl());
        response.setCertificateUrl(product.getCertificateUrl());
        
        // Metadata
        response.setIsActive(product.getIsActive());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        
        return response;
    }

    /**
     * Generate unique batch number
     */
    private String generateBatchNumber() {
        return "BATCH-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Generate unique QR code
     */
    private String generateQRCode() {
        return "QR-" + UUID.randomUUID().toString().toUpperCase();
    }

    /**
     * Inner class for dashboard response
     */
    public static class FarmerDashboardResponse {
        private Long totalProducts;
        private Long activeProducts;
        private Long soldProducts;
        private List<ProductResponse> recentProducts;

        public Long getTotalProducts() { return totalProducts; }
        public void setTotalProducts(Long totalProducts) { this.totalProducts = totalProducts; }
        
        public Long getActiveProducts() { return activeProducts; }
        public void setActiveProducts(Long activeProducts) { this.activeProducts = activeProducts; }
        
        public Long getSoldProducts() { return soldProducts; }
        public void setSoldProducts(Long soldProducts) { this.soldProducts = soldProducts; }
        
        public List<ProductResponse> getRecentProducts() { return recentProducts; }
        public void setRecentProducts(List<ProductResponse> recentProducts) { this.recentProducts = recentProducts; }
    }

    // ==================== DISTRIBUTOR METHODS ====================

    /**
     * Get all products available for distributors (WITH_FARMER or QUALITY_CHECKED status)
     */
    public List<ProductResponse> getAvailableProductsForDistributor() {
        List<Product> products = productRepository.findByStatusAndIsActive(ProductStatus.QUALITY_CHECKED, true);
        List<Product> farmerProducts = productRepository.findByStatusAndIsActive(ProductStatus.WITH_FARMER, true);
        products.addAll(farmerProducts);
        return products.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Request product transfer from farmer to distributor
     */
    @Transactional
    public ProductResponse requestProductTransfer(Long productId, Long distributorId, java.math.BigDecimal requestedQuantity, java.math.BigDecimal price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getIsActive()) {
            throw new RuntimeException("Product is not active");
        }

        if (product.getStatus() != ProductStatus.QUALITY_CHECKED && product.getStatus() != ProductStatus.WITH_FARMER) {
            throw new RuntimeException("Product is not available for transfer");
        }

        if (requestedQuantity.compareTo(product.getQuantity()) > 0) {
            throw new RuntimeException("Requested quantity exceeds available quantity");
        }

        User distributor = userRepository.findById(distributorId)
                .orElseThrow(() -> new RuntimeException("Distributor not found"));

        // Update product ownership and status
        product.setCurrentOwnerId(distributorId);
        product.setCurrentOwnerName(distributor.getUsername());
        product.setStatus(ProductStatus.WITH_DISTRIBUTOR);
        product.setCurrentPrice(price);
        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    /**
     * Get all products owned by distributor
     */
    public List<ProductResponse> getDistributorProducts(Long distributorId) {
        List<Product> products = productRepository.findByCurrentOwnerIdAndIsActive(distributorId, true);
        return products.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Update product price by distributor
     */
    @Transactional
    public ProductResponse updateDistributorPrice(Long productId, Long distributorId, java.math.BigDecimal newPrice) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getCurrentOwnerId().equals(distributorId)) {
            throw new RuntimeException("You can only update price for products you own");
        }

        if (product.getStatus() != ProductStatus.WITH_DISTRIBUTOR) {
            throw new RuntimeException("Product is not with distributor");
        }

        product.setCurrentPrice(newPrice);
        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    /**
     * Transfer product from distributor to retailer
     */
    @Transactional
    public ProductResponse transferToRetailer(Long productId, Long distributorId, Long retailerId, java.math.BigDecimal quantity, java.math.BigDecimal price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getCurrentOwnerId().equals(distributorId)) {
            throw new RuntimeException("You can only transfer products you own");
        }

        if (product.getStatus() != ProductStatus.WITH_DISTRIBUTOR) {
            throw new RuntimeException("Product is not with distributor");
        }

        if (quantity.compareTo(product.getQuantity()) > 0) {
            throw new RuntimeException("Transfer quantity exceeds available quantity");
        }

        User retailer = userRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));

        // Update product ownership and status
        product.setCurrentOwnerId(retailerId);
        product.setCurrentOwnerName(retailer.getUsername());
        product.setStatus(ProductStatus.WITH_RETAILER);
        product.setCurrentPrice(price);
        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    /**
     * Get distributor dashboard statistics
     */
    public DistributorDashboardResponse getDistributorDashboard(Long distributorId) {
        User distributor = userRepository.findById(distributorId)
                .orElseThrow(() -> new RuntimeException("Distributor not found"));

        List<Product> allProducts = productRepository.findByCurrentOwnerId(distributorId);
        
        long totalProducts = allProducts.size();
        long activeProducts = allProducts.stream().filter(Product::getIsActive).count();
        long withDistributor = allProducts.stream()
                .filter(p -> p.getStatus() == ProductStatus.WITH_DISTRIBUTOR && p.getIsActive())
                .count();
        long transferredToRetailer = productRepository.findByStatus(ProductStatus.WITH_RETAILER).stream()
                .filter(p -> p.getFarmer().getId().equals(distributorId) || 
                           (p.getCurrentOwnerId() != null && !p.getCurrentOwnerId().equals(distributorId)))
                .count();

        List<ProductResponse> recentProducts = allProducts.stream()
                .filter(Product::getIsActive)
                .sorted((p1, p2) -> p2.getUpdatedAt().compareTo(p1.getUpdatedAt()))
                .limit(5)
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        DistributorDashboardResponse response = new DistributorDashboardResponse();
        response.setTotalProducts(totalProducts);
        response.setActiveProducts(activeProducts);
        response.setWithDistributor(withDistributor);
        response.setTransferredToRetailer(transferredToRetailer);
        response.setRecentProducts(recentProducts);

        return response;
    }

    /**
     * Distributor Dashboard Response DTO
     */
    public static class DistributorDashboardResponse {
        private Long totalProducts;
        private Long activeProducts;
        private Long withDistributor;
        private Long transferredToRetailer;
        private List<ProductResponse> recentProducts;

        public Long getTotalProducts() { return totalProducts; }
        public void setTotalProducts(Long totalProducts) { this.totalProducts = totalProducts; }
        
        public Long getActiveProducts() { return activeProducts; }
        public void setActiveProducts(Long activeProducts) { this.activeProducts = activeProducts; }
        
        public Long getWithDistributor() { return withDistributor; }
        public void setWithDistributor(Long withDistributor) { this.withDistributor = withDistributor; }
        
        public Long getTransferredToRetailer() { return transferredToRetailer; }
        public void setTransferredToRetailer(Long transferredToRetailer) { this.transferredToRetailer = transferredToRetailer; }
        
        public List<ProductResponse> getRecentProducts() { return recentProducts; }
        public void setRecentProducts(List<ProductResponse> recentProducts) { this.recentProducts = recentProducts; }
    }

    // ==================== RETAILER METHODS ====================

    /**
     * Get all products available for retailers (WITH_DISTRIBUTOR status)
     */
    public List<ProductResponse> getAvailableProductsForRetailer() {
        List<Product> products = productRepository.findByStatusAndIsActive(ProductStatus.WITH_DISTRIBUTOR, true);
        return products.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Request product from distributor
     */
    @Transactional
    public ProductResponse requestProductFromDistributor(Long productId, Long retailerId, java.math.BigDecimal requestedQuantity, java.math.BigDecimal price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getIsActive()) {
            throw new RuntimeException("Product is not active");
        }

        if (product.getStatus() != ProductStatus.WITH_DISTRIBUTOR) {
            throw new RuntimeException("Product is not available for purchase");
        }

        if (requestedQuantity.compareTo(product.getQuantity()) > 0) {
            throw new RuntimeException("Requested quantity exceeds available quantity");
        }

        User retailer = userRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));

        // Update product ownership and status
        product.setCurrentOwnerId(retailerId);
        product.setCurrentOwnerName(retailer.getUsername());
        product.setStatus(ProductStatus.WITH_RETAILER);
        product.setCurrentPrice(price);
        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    /**
     * Get all products owned by retailer
     */
    public List<ProductResponse> getRetailerProducts(Long retailerId) {
        List<Product> products = productRepository.findByCurrentOwnerIdAndIsActive(retailerId, true);
        return products.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Update product price by retailer
     */
    @Transactional
    public ProductResponse updateRetailerPrice(Long productId, Long retailerId, java.math.BigDecimal newPrice) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getCurrentOwnerId().equals(retailerId)) {
            throw new RuntimeException("You can only update price for products you own");
        }

        if (product.getStatus() != ProductStatus.WITH_RETAILER) {
            throw new RuntimeException("Product is not with retailer");
        }

        product.setCurrentPrice(newPrice);
        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    /**
     * Mark product as sold
     */
    @Transactional
    public ProductResponse markProductAsSold(Long productId, Long retailerId, java.math.BigDecimal soldQuantity, java.math.BigDecimal finalPrice, Long customerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getCurrentOwnerId().equals(retailerId)) {
            throw new RuntimeException("You can only sell products you own");
        }

        if (product.getStatus() != ProductStatus.WITH_RETAILER) {
            throw new RuntimeException("Product is not with retailer");
        }

        if (soldQuantity.compareTo(product.getQuantity()) > 0) {
            throw new RuntimeException("Sold quantity exceeds available quantity");
        }

        // Update product status to SOLD
        product.setStatus(ProductStatus.SOLD);
        product.setCurrentPrice(finalPrice);
        product.setQuantity(soldQuantity);
        product.setUpdatedAt(LocalDateTime.now());

        // If customer ID provided, update current owner
        if (customerId != null) {
            User customer = userRepository.findById(customerId)
                    .orElse(null);
            if (customer != null) {
                product.setCurrentOwnerId(customerId);
                product.setCurrentOwnerName(customer.getUsername());
            }
        }

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    /**
     * Get retailer dashboard statistics
     */
    public RetailerDashboardResponse getRetailerDashboard(Long retailerId) {
        User retailer = userRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));

        List<Product> allProducts = productRepository.findByCurrentOwnerId(retailerId);
        
        long totalProducts = allProducts.size();
        long activeProducts = allProducts.stream().filter(Product::getIsActive).count();
        long withRetailer = allProducts.stream()
                .filter(p -> p.getStatus() == ProductStatus.WITH_RETAILER && p.getIsActive())
                .count();
        long soldProducts = productRepository.findByStatus(ProductStatus.SOLD).stream()
                .filter(p -> {
                    // Check if this retailer was involved in the sale
                    return p.getCurrentOwnerId() != null && 
                           (p.getCurrentOwnerId().equals(retailerId) || 
                            allProducts.stream().anyMatch(ap -> ap.getId().equals(p.getId())));
                })
                .count();

        // Calculate revenue from sold products
        java.math.BigDecimal revenue = productRepository.findByStatus(ProductStatus.SOLD).stream()
                .filter(p -> allProducts.stream().anyMatch(ap -> ap.getId().equals(p.getId())))
                .map(Product::getCurrentPrice)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        List<ProductResponse> recentProducts = allProducts.stream()
                .filter(Product::getIsActive)
                .sorted((p1, p2) -> p2.getUpdatedAt().compareTo(p1.getUpdatedAt()))
                .limit(5)
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        RetailerDashboardResponse response = new RetailerDashboardResponse();
        response.setTotalProducts(totalProducts);
        response.setActiveProducts(activeProducts);
        response.setWithRetailer(withRetailer);
        response.setSoldProducts(soldProducts);
        response.setRevenue(revenue);
        response.setRecentProducts(recentProducts);

        return response;
    }

    /**
     * Retailer Dashboard Response DTO
     */
    public static class RetailerDashboardResponse {
        private Long totalProducts;
        private Long activeProducts;
        private Long withRetailer;
        private Long soldProducts;
        private java.math.BigDecimal revenue;
        private List<ProductResponse> recentProducts;

        public Long getTotalProducts() { return totalProducts; }
        public void setTotalProducts(Long totalProducts) { this.totalProducts = totalProducts; }
        
        public Long getActiveProducts() { return activeProducts; }
        public void setActiveProducts(Long activeProducts) { this.activeProducts = activeProducts; }
        
        public Long getWithRetailer() { return withRetailer; }
        public void setWithRetailer(Long withRetailer) { this.withRetailer = withRetailer; }
        
        public Long getSoldProducts() { return soldProducts; }
        public void setSoldProducts(Long soldProducts) { this.soldProducts = soldProducts; }
        
        public java.math.BigDecimal getRevenue() { return revenue; }
        public void setRevenue(java.math.BigDecimal revenue) { this.revenue = revenue; }
        
        public List<ProductResponse> getRecentProducts() { return recentProducts; }
        public void setRecentProducts(List<ProductResponse> recentProducts) { this.recentProducts = recentProducts; }
    }

    // ==================== CONSUMER METHODS ====================

    /**
     * Get all products (for consumers to browse)
     */
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findByIsActive(true);
        return products.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Get product with complete journey/traceability
     */
    public ProductJourneyResponse getProductWithJourney(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductResponse productResponse = mapToResponse(product);
        List<JourneyStep> journey = buildProductJourney(product);

        ProductJourneyResponse response = new ProductJourneyResponse();
        response.setProduct(productResponse);
        response.setJourney(journey);
        response.setTotalSteps(journey.size());
        response.setDaysInSupplyChain(calculateDaysInSupplyChain(product));
        response.setCurrentStage(getCurrentStage(product.getStatus()));
        response.setVerified(true);
        response.setQrCode(product.getQrCode());
        response.setBatchNumber(product.getBatchNumber());

        return response;
    }

    /**
     * Get product by QR code
     */
    public ProductJourneyResponse getProductByQrCode(String qrCode) {
        Product product = productRepository.findByQrCode(qrCode)
                .orElseThrow(() -> new RuntimeException("Product not found with QR code: " + qrCode));
        
        return getProductWithJourney(product.getId());
    }

    /**
     * Search products with filters
     */
    public List<ProductResponse> searchProducts(String name, ProductCategory category, ProductStatus status, 
                                                  String city, String state) {
        List<Product> allProducts = productRepository.findByIsActive(true);
        
        return allProducts.stream()
                .filter(p -> name == null || p.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(p -> category == null || p.getCategory() == category)
                .filter(p -> status == null || p.getStatus() == status)
                .filter(p -> city == null || (p.getCity() != null && p.getCity().equalsIgnoreCase(city)))
                .filter(p -> state == null || (p.getState() != null && p.getState().equalsIgnoreCase(state)))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Build complete product journey timeline
     */
    private List<JourneyStep> buildProductJourney(Product product) {
        List<JourneyStep> journey = new ArrayList<>();

        // Step 1: Farmer created/registered product
        JourneyStep farmerStep = new JourneyStep();
        farmerStep.setStage("FARMER");
        farmerStep.setActorName(product.getFarmer().getUsername());
        farmerStep.setActorRole("Farmer");
        farmerStep.setStatus(ProductStatus.REGISTERED);
        farmerStep.setPrice(product.getFarmerPrice());
        farmerStep.setTimestamp(product.getCreatedAt());
        farmerStep.setLocation(product.getFarmLocation() + ", " + product.getCity() + ", " + product.getState());
        farmerStep.setNotes("Product registered and quality checked");
        journey.add(farmerStep);

        // Step 2: If product reached distributor
        if (product.getStatus() == ProductStatus.WITH_DISTRIBUTOR || 
            product.getStatus() == ProductStatus.WITH_RETAILER || 
            product.getStatus() == ProductStatus.SOLD) {
            
            JourneyStep distributorStep = new JourneyStep();
            distributorStep.setStage("DISTRIBUTOR");
            distributorStep.setActorName("Distributor");
            distributorStep.setActorRole("Distributor");
            distributorStep.setStatus(ProductStatus.WITH_DISTRIBUTOR);
            distributorStep.setPrice(product.getCurrentPrice());
            distributorStep.setTimestamp(product.getUpdatedAt());
            distributorStep.setLocation("Distribution Center");
            distributorStep.setNotes("Product acquired for distribution");
            journey.add(distributorStep);
        }

        // Step 3: If product reached retailer
        if (product.getStatus() == ProductStatus.WITH_RETAILER || 
            product.getStatus() == ProductStatus.SOLD) {
            
            JourneyStep retailerStep = new JourneyStep();
            retailerStep.setStage("RETAILER");
            retailerStep.setActorName(product.getCurrentOwnerName() != null ? product.getCurrentOwnerName() : "Retailer");
            retailerStep.setActorRole("Retailer");
            retailerStep.setStatus(ProductStatus.WITH_RETAILER);
            retailerStep.setPrice(product.getCurrentPrice());
            retailerStep.setTimestamp(product.getUpdatedAt());
            retailerStep.setLocation("Retail Store");
            retailerStep.setNotes("Product available for sale");
            journey.add(retailerStep);
        }

        // Step 4: If product was sold
        if (product.getStatus() == ProductStatus.SOLD) {
            JourneyStep soldStep = new JourneyStep();
            soldStep.setStage("CONSUMER");
            soldStep.setActorName(product.getCurrentOwnerName() != null ? product.getCurrentOwnerName() : "Consumer");
            soldStep.setActorRole("Consumer");
            soldStep.setStatus(ProductStatus.SOLD);
            soldStep.setPrice(product.getCurrentPrice());
            soldStep.setTimestamp(product.getUpdatedAt());
            soldStep.setLocation("Final Destination");
            soldStep.setNotes("Product sold to consumer");
            journey.add(soldStep);
        }

        return journey;
    }

    /**
     * Calculate days in supply chain
     */
    private int calculateDaysInSupplyChain(Product product) {
        if (product.getCreatedAt() == null) {
            return 0;
        }
        LocalDateTime now = product.getUpdatedAt() != null ? product.getUpdatedAt() : LocalDateTime.now();
        return (int) ChronoUnit.DAYS.between(product.getCreatedAt(), now);
    }

    /**
     * Get current stage name from status
     */
    private String getCurrentStage(ProductStatus status) {
        switch (status) {
            case REGISTERED:
            case HARVESTED:
            case QUALITY_CHECKED:
            case WITH_FARMER:
                return "FARMER";
            case WITH_DISTRIBUTOR:
                return "DISTRIBUTOR";
            case WITH_RETAILER:
                return "RETAILER";
            case SOLD:
                return "CONSUMER";
            default:
                return "UNKNOWN";
        }
    }

    // ==================== ADMIN METHODS ====================

    /**
     * Get all products (Admin)
     */
    public List<ProductResponse> getAllProductsAdmin() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Update product status (Admin override)
     */
    @Transactional
    public ProductResponse adminUpdateProductStatus(Long productId, ProductStatus newStatus, String notes) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStatus(newStatus);
        product.setUpdatedAt(LocalDateTime.now());
        
        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    /**
     * Delete any product (Admin)
     */
    @Transactional
    public void adminDeleteProduct(Long productId, boolean permanent) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (permanent) {
            productRepository.delete(product);
        } else {
            product.setIsActive(false);
            product.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);
        }
    }

    /**
     * Get system-wide statistics (Admin)
     */
    public SystemStatsResponse getSystemStats() {
        SystemStatsResponse stats = new SystemStatsResponse();

        // Product statistics
        List<Product> allProducts = productRepository.findAll();
        stats.setTotalProducts((long) allProducts.size());
        stats.setActiveProducts(allProducts.stream().filter(Product::getIsActive).count());
        stats.setRegisteredProducts(allProducts.stream().filter(p -> p.getStatus() == ProductStatus.REGISTERED).count());
        stats.setHarvestedProducts(allProducts.stream().filter(p -> p.getStatus() == ProductStatus.HARVESTED).count());
        stats.setQualityCheckedProducts(allProducts.stream().filter(p -> p.getStatus() == ProductStatus.QUALITY_CHECKED).count());
        stats.setProductsWithFarmers(allProducts.stream().filter(p -> p.getStatus() == ProductStatus.WITH_FARMER).count());
        stats.setProductsWithDistributors(allProducts.stream().filter(p -> p.getStatus() == ProductStatus.WITH_DISTRIBUTOR).count());
        stats.setProductsWithRetailers(allProducts.stream().filter(p -> p.getStatus() == ProductStatus.WITH_RETAILER).count());
        stats.setSoldProducts(allProducts.stream().filter(p -> p.getStatus() == ProductStatus.SOLD).count());

        // Financial statistics
        java.math.BigDecimal totalRevenue = allProducts.stream()
                .filter(p -> p.getStatus() == ProductStatus.SOLD && p.getCurrentPrice() != null)
                .map(Product::getCurrentPrice)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        stats.setTotalRevenue(totalRevenue);

        if (!allProducts.isEmpty()) {
            java.math.BigDecimal avgPrice = allProducts.stream()
                    .filter(p -> p.getCurrentPrice() != null)
                    .map(Product::getCurrentPrice)
                    .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add)
                    .divide(java.math.BigDecimal.valueOf(allProducts.size()), 2, java.math.BigDecimal.ROUND_HALF_UP);
            stats.setAverageProductPrice(avgPrice);
        }

        // Supply chain metrics
        double avgDays = allProducts.stream()
                .filter(p -> p.getCreatedAt() != null)
                .mapToInt(this::calculateDaysInSupplyChain)
                .average()
                .orElse(0.0);
        stats.setAverageSupplyChainDays(avgDays);

        // Calculate average markup
        double avgMarkup = allProducts.stream()
                .filter(p -> p.getFarmerPrice() != null && p.getCurrentPrice() != null 
                        && p.getFarmerPrice().compareTo(java.math.BigDecimal.ZERO) > 0)
                .mapToDouble(p -> {
                    double markup = p.getCurrentPrice().subtract(p.getFarmerPrice())
                            .divide(p.getFarmerPrice(), 4, java.math.BigDecimal.ROUND_HALF_UP)
                            .multiply(java.math.BigDecimal.valueOf(100))
                            .doubleValue();
                    return markup;
                })
                .average()
                .orElse(0.0);
        stats.setAveragePriceMarkup(avgMarkup);

        return stats;
    }
}
