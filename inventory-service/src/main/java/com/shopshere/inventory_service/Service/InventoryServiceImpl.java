package com.shopshere.inventory_service.Service;

import com.shopshere.inventory_service.Client.ProductClient;
import com.shopshere.inventory_service.DTO.InventoryRequest;
import com.shopshere.inventory_service.DTO.InventoryResponse;
import com.shopshere.inventory_service.DTO.ProductResponse;
import com.shopshere.inventory_service.Entity.Inventory;
import com.shopshere.inventory_service.Event.OrderCreatedEvent;
import com.shopshere.inventory_service.Event.OrderItemEvent;
import com.shopshere.inventory_service.Repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;
    private final ProductClient productClient;

    public InventoryResponse createInventory(InventoryRequest request) {

        ProductResponse product = productClient.getProductById(request.getProductId());
        Inventory inventory = new Inventory();

        inventory.setProductId(product.getId());
        inventory.setQuantity(request.getQuantity());
        inventory.setUpdatedAt(LocalDateTime.now());

        Inventory savedInventory = inventoryRepository.save(inventory);

        InventoryResponse response = new InventoryResponse();

        response.setId(savedInventory.getId());
        response.setProductId(savedInventory.getProductId());
        response.setQuantity(savedInventory.getQuantity());

        return response;

    }

    @Override
    public InventoryResponse getInventoryByProductId(Long productId) {

        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(() -> new RuntimeException("Inventory Not Found"));

        InventoryResponse response = new InventoryResponse();

        response.setId(inventory.getId());
        response.setProductId(inventory.getProductId());
        response.setQuantity(inventory.getQuantity());

        return response;
    }

    @Transactional
    public void reserveStock(Long productId, Integer quantity) {

        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(() -> new RuntimeException("Inventory Not Found"));

        if(inventory.getQuantity() < quantity){
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setQuantity(inventory.getQuantity()-quantity);
        inventoryRepository.save(inventory);
    }

    public void reserveInventory(OrderCreatedEvent event) {

        for (OrderItemEvent item : event.getItems()) {

            Inventory inventory = inventoryRepository
                    .findByProductId(item.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Inventory not found for product: "
                                            + item.getProductId()
                            ));

            if (inventory.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient inventory for product: " + item.getProductId());
            }

            inventory.setQuantity(inventory.getQuantity() - item.getQuantity());

            inventoryRepository.save(inventory);
        }
    }
}
