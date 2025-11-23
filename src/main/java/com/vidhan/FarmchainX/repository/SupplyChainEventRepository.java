package com.vidhan.FarmchainX.repository;

import com.vidhan.FarmchainX.entity.SupplyChainEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyChainEventRepository extends JpaRepository<SupplyChainEvent, String> {

    @Query("SELECT e FROM SupplyChainEvent e WHERE e.product.id = :productId ORDER BY e.timestamp DESC")
    List<SupplyChainEvent> findByProductIdOrderByTimestampDesc(@Param("productId") String productId);

    @Query("SELECT e FROM SupplyChainEvent e WHERE e.product.batchId = :batchId ORDER BY e.timestamp DESC")
    List<SupplyChainEvent> findByProductBatchIdOrderByTimestampDesc(@Param("batchId") String batchId);
}
