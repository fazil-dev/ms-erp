package com.fazil.ms.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderLineItemRequest(
        String skuCode,
        BigDecimal price,
        Integer quantity
) {
}
