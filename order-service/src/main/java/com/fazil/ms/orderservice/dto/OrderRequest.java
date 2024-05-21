package com.fazil.ms.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderRequest(
        @Valid
        @NotEmpty (message = "Order line item must required.")
        List<OrderLineItemRequest> orderLineItems
) {
}
