package com.fazil.ms.productservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

import java.math.BigDecimal;

@JsonSerialize
@Builder
public record ProductResponse(String id, String name, String description, BigDecimal price) {
}
