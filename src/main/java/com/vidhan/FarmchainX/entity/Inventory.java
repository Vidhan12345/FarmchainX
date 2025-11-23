package com.vidhan.FarmchainX.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner; // Distributor or Retailer

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDateTime receivedDate;

    private Double temperature;
    private String storageCondition;
    private String location;

    @Enumerated(EnumType.STRING)
    private InventoryStatus status;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
