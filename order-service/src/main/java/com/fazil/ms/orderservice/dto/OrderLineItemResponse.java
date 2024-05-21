package com.fazil.ms.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderLineItemResponse(
        Long id,
        BigDecimal price,
        Integer quantity
) {
}
