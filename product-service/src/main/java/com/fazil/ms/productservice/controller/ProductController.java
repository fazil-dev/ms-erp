package com.fazil.ms.productservice.controller;

import com.fazil.ms.productservice.dto.ProductRequest;
import com.fazil.ms.productservice.dto.ProductResponse;
import com.fazil.ms.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponse createProduct(@RequestBody @Valid ProductRequest productRequest) {
        log.info("Init ProductController:createProduct for product name {}",productRequest.name());
        ProductResponse productResponse = productService.createProduct(productRequest);
        return productResponse;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        log.info("Init ProductController:getAllProducts");
        return productService.getAllProducts();
    }

}
