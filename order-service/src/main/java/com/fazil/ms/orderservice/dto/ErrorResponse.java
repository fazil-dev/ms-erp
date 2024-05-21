package com.fazil.ms.orderservice.dto;

public record ErrorResponse(
        String stauts,
        String message,
        String description
) {
}
