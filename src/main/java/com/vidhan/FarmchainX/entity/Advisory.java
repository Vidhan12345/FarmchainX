package com.vidhan.FarmchainX.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "advisories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advisory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;

    @Column(nullable = false)
    private String mentorName;

    @Column(nullable = false, length = 2000)
    private String advice;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    @Column(nullable = false)
    private String cropType;

    private String category; // "Pest Control", "Irrigation", "Fertilization"
    private String priority; // "HIGH", "MEDIUM", "LOW"
}
