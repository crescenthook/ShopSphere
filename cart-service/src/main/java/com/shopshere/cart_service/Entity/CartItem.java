package com.shopshere.cart_service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_items", uniqueConstraints = {@UniqueConstraint(name = "uk_cart_products" , columnNames = {"cart_id", "product_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="cart_id", nullable = false)
    private Cart cart;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
