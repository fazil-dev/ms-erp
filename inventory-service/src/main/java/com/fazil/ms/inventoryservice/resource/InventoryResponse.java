package com.fazil.ms.inventoryservice.resource;

import lombok.Builder;

@Builder
public record InventoryResponse (
        Long id,
        String skuCode,
        Integer quantity,
        Boolean isInStock
) {
    @Override
    public Boolean isInStock() {
        return quantity > 0;
    }
}
