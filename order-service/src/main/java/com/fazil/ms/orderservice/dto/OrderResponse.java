package com.fazil.ms.orderservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderResponse(
        Long id,
        String orderNumber,
        List<OrderLineItemResponse> orderLineItems
) {
}
