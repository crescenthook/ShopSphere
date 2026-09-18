package com.shopshere.inventory_service.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEvent {

    private Long productId;

    private Integer quantity;

    @Override
    public String toString() {
        return "OrderItemEvent{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
