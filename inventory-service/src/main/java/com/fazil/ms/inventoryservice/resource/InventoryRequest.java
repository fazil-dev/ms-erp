package com.fazil.ms.inventoryservice.resource;

import lombok.Builder;

@Builder
public record InventoryRequest(
        String skuCode,

        Integer quantity
) {
}
