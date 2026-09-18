package com.shopshere.inventory_service.Consumer;

import com.shopshere.inventory_service.Event.OrderCreatedEvent;
import com.shopshere.inventory_service.Service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventConsumer {

    private final InventoryService inventoryService;

    public InventoryEventConsumer(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "order-created" , groupId = "inventory-service-group")
    public void handleOrderCreated(OrderCreatedEvent event){

        System.out.println("Received OrderCreatedEvent: " + event);
        inventoryService.reserveInventory(event);
    }
}
