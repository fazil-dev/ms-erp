package com.fazil.ms.orderservice.dto;

public record InventoryResponse(
        String skuCode,
        Boolean isInStock
) {
}
