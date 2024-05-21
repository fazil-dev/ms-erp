package com.fazil.ms.productservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonSerialize
public record ProductRequest(
        @NotNull(message = "Name should not be empty")
        String name, String description, BigDecimal price) {
}
