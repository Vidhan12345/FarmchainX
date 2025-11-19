package com.vidhan.FarmchainX.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", unique = true, nullable = false)
    private ERole roleName;

    private String description;

    // Custom constructor without ID (since it's auto-generated)
    public Role(ERole roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }
}
