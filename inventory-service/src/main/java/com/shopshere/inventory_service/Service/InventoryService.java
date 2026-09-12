package com.shopshere.inventory_service.Service;

import com.shopshere.inventory_service.DTO.InventoryRequest;
import com.shopshere.inventory_service.DTO.InventoryResponse;
import com.shopshere.inventory_service.Entity.Inventory;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest request);

    InventoryResponse getInventoryByProductId(Long productId);

    void reserveStock(Long productId, Integer quantity);
}
