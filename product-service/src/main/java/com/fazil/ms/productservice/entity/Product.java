package com.fazil.ms.productservice.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("product")
@Builder
public record Product (
    @Id
    String id,
    String name,
    String description,
    BigDecimal price

) {

}
