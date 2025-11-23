package com.vidhan.FarmchainX.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "supply_chain_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyChainEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String type; // "Harvest", "Quality Check", "Transit Start", etc.

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String actor; // Name of person/company

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_user_id")
    private User actorUser;

    @Column(nullable = false, length = 1000)
    private String description;

    private Double temperature;
    private String humidity;

    @Column(name = "product_condition")
    private String productCondition; // "Good", "Damaged"

    private String blockchainHash;
}
