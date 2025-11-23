package com.vidhan.FarmchainX.repository;

import com.vidhan.FarmchainX.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query("SELECT r FROM Review r WHERE r.product.id = :productId ORDER BY r.createdAt DESC")
    List<Review> findByProductIdOrderByCreatedAtDesc(@Param("productId") String productId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId")
    Double getAverageRatingByProductId(@Param("productId") String productId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId")
    Long countByProductId(@Param("productId") String productId);
}
