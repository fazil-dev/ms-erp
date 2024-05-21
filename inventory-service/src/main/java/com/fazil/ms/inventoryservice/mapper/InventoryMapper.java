package com.fazil.ms.inventoryservice.mapper;

import com.fazil.ms.inventoryservice.entity.Inventory;
import com.fazil.ms.inventoryservice.resource.InventoryRequest;
import com.fazil.ms.inventoryservice.resource.InventoryResponse;

public class InventoryMapper {

    private InventoryMapper(){
    }
    public static Inventory convert(InventoryRequest inventoryRequest) {
        return Inventory.builder()
                .skuCode(inventoryRequest.skuCode())
                .quantity(inventoryRequest.quantity())
                .build();
    }

    public static InventoryResponse convert(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity())
                .build();
    }
}
