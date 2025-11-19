package com.vidhan.FarmchainX.repository;

import com.vidhan.FarmchainX.entity.Product;
import com.vidhan.FarmchainX.entity.ProductCategory;
import com.vidhan.FarmchainX.entity.ProductStatus;
import com.vidhan.FarmchainX.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Find by farmer
    List<Product> findByFarmer(User farmer);
    List<Product> findByFarmerAndIsActive(User farmer, Boolean isActive);
    
    // Find by current owner
    List<Product> findByCurrentOwner(User owner);
    List<Product> findByCurrentOwnerId(Long currentOwnerId);
    List<Product> findByCurrentOwnerIdAndIsActive(Long currentOwnerId, Boolean isActive);
    
    // Find by status
    List<Product> findByStatus(ProductStatus status);
    List<Product> findByStatusIn(List<ProductStatus> statuses);
    
    // Find by category
    List<Product> findByCategory(ProductCategory category);
    
    // Find by QR code or batch number
    Optional<Product> findByQrCode(String qrCode);
    Optional<Product> findByBatchNumber(String batchNumber);
    
    // Find available products for purchase
    List<Product> findByStatusAndIsActive(ProductStatus status, Boolean isActive);
    
    // Find by active status
    List<Product> findByIsActive(Boolean isActive);
    
    // Count products by farmer
    Long countByFarmer(User farmer);
    
    // Count by current owner and status
    Long countByCurrentOwnerIdAndStatus(Long currentOwnerId, ProductStatus status);
    Long countByCurrentOwnerIdAndIsActive(Long currentOwnerId, Boolean isActive);
}
