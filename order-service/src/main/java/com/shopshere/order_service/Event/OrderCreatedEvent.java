package com.shopshere.order_service.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;

    private List<OrderItemEvent> items;
}
