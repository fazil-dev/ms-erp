package com.fazil.ms.productservice;

import com.fazil.ms.productservice.controller.ProductController;
import com.fazil.ms.productservice.dto.ProductRequest;
import com.fazil.ms.productservice.dto.ProductResponse;
import com.fazil.ms.productservice.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductServiceApplicationUnitTest {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    @BeforeEach
    public void setupEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create Product")
    void shouldCreateProduct() {
        when(productService.createProduct(any())).thenReturn(
                getDefaultProductResponse()
        );

        ProductResponse productResponse = productController.createProduct(getDefaultProductRequest());
        assertAll(
                () -> assertEquals(productResponse.name(), getDefaultProductResponse().name()),
                () -> assertEquals(productResponse.price(), getDefaultProductResponse().price())
        );

    }

    private static ProductResponse getDefaultProductResponse() {
        return ProductResponse.builder()
                .id("663db13ef4efdf01a0f7100b")
                .name("Apple 11 pro 512")
                .description("Apple 11 pro 512 GB")
                .price(BigDecimal.valueOf(6199.00))
                .build();
    }

    private static ProductRequest getDefaultProductRequest() {
        return ProductRequest.builder()
                .name("Apple 11 pro 512")
                .description("Apple 11 pro 512 GB")
                .price(BigDecimal.valueOf(6199.00))
                .build();
    }
}
