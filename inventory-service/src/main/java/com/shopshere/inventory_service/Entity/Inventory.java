package com.shopshere.inventory_service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory", uniqueConstraints = {@UniqueConstraint(name = "uk_inventory_product", columnNames = "product_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false, unique = true)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
