package com.shopshere.inventory_service.Controller;

import com.shopshere.inventory_service.DTO.InventoryRequest;
import com.shopshere.inventory_service.DTO.InventoryResponse;
import com.shopshere.inventory_service.Service.InventoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(@RequestBody InventoryRequest request) {

        InventoryResponse response = inventoryService.createInventory(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable Long productId) {

        InventoryResponse response = inventoryService.getInventoryByProductId(productId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{productId}/reserve")
    public ResponseEntity<Void> reserveStock(@PathVariable Long productId, @RequestParam Integer quantity) {

        inventoryService.reserveStock(productId, quantity);

        return ResponseEntity.ok().build();
    }
}