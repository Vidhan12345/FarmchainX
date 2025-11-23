package com.vidhan.FarmchainX.repository;

import com.vidhan.FarmchainX.entity.Product;
import com.vidhan.FarmchainX.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // Find by farmer ID
    @Query("SELECT p FROM Product p WHERE p.farmer.id = :farmerId")
    List<Product> findByFarmerId(@Param("farmerId") String farmerId);

    // Find by status
    List<Product> findByStatus(ProductStatus status);

    List<Product> findByStatusIn(List<ProductStatus> statuses);

    // Find by batch ID
    Optional<Product> findByBatchId(String batchId);

    // Search by crop type
    List<Product> findByCropTypeContainingIgnoreCase(String cropType);

    // Search by product name
    List<Product> findByProductNameContainingIgnoreCase(String productName);

    // Find organic products
    List<Product> findByOrganic(Boolean organic);

    // Find by status and organic
    List<Product> findByStatusAndOrganic(ProductStatus status, Boolean organic);

    // Count products by farmer
    @Query("SELECT COUNT(p) FROM Product p WHERE p.farmer.id = :farmerId")
    Long countByFarmerId(@Param("farmerId") String farmerId);

    // Count by status
    Long countByStatus(ProductStatus status);
}
