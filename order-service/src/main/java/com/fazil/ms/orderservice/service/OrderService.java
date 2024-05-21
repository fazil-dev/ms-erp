package com.fazil.ms.orderservice.service;

import com.fazil.ms.orderservice.dto.InventoryResponse;
import com.fazil.ms.orderservice.dto.OrderRequest;
import com.fazil.ms.orderservice.dto.OrderResponse;
import com.fazil.ms.orderservice.entity.Order;
import com.fazil.ms.orderservice.entity.OrderLineItem;
import com.fazil.ms.orderservice.mapper.OrderMapper;
import com.fazil.ms.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    private OrderRepository orderRepository;

    private WebClient webClient;

    public OrderService(OrderRepository orderRepository, WebClient webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }

    public OrderResponse placeOrder(OrderRequest orderRequest) {
        log.info("Order service place order method invoked");
        Order order = OrderMapper.toOrder(orderRequest);
        List<String> skuCodes = order.getOrderLineItems().stream()
                .map(OrderLineItem::getSkuCode)
                .collect(Collectors.toList());

        //call inventory service to check inventory is a ailable
        InventoryResponse[] inventoryResponses = webClient.get()
                    .uri("http://localhost:9192/api/v1/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                            .bodyToMono(InventoryResponse[].class)
                    .block();
        if (skuCodes.size() != inventoryResponses.length) {
            throw new RuntimeException("Inventory is not available for some products");
        }
        var isAllProductInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        if (isAllProductInStock) {
            orderRepository.save(order);
            OrderResponse orderResponse = OrderMapper.toOrderResponse(order);
            log.info("Order service place order method finished with order number {}.", orderResponse.orderNumber());
            return orderResponse;
        } else {
            throw new RuntimeException("Product is not in stock");
        }

    }

}
