package com.shopshere.inventory_service.Service;

import com.shopshere.inventory_service.DTO.InventoryRequest;
import com.shopshere.inventory_service.DTO.InventoryResponse;
import com.shopshere.inventory_service.Entity.Inventory;
import com.shopshere.inventory_service.Event.OrderCreatedEvent;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest request);

    InventoryResponse getInventoryByProductId(Long productId);

    void reserveStock(Long productId, Integer quantity);

    void reserveInventory(OrderCreatedEvent event);
}
