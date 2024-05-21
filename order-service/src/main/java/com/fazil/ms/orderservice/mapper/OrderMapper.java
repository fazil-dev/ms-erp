package com.fazil.ms.orderservice.mapper;

import com.fazil.ms.orderservice.dto.OrderLineItemResponse;
import com.fazil.ms.orderservice.dto.OrderRequest;
import com.fazil.ms.orderservice.dto.OrderResponse;
import com.fazil.ms.orderservice.entity.Order;
import com.fazil.ms.orderservice.entity.OrderLineItem;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderLineItems(buildOrderLineItemsResponse(order.getOrderLineItems()))
                .build();
    }
    private static List<OrderLineItemResponse> buildOrderLineItemsResponse(List<OrderLineItem> orderLineItems) {
        return orderLineItems.stream().map(
                orderLineItem -> OrderLineItemResponse.builder()
                        .id(orderLineItem.getId())
                        .price(orderLineItem.getPrice())
                        .quantity(orderLineItem.getQuantity())
                        .build()
        ).collect(Collectors.toList());
    }


    public static Order toOrder(OrderRequest orderRequest) {
        return Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItems(
                        orderRequest.orderLineItems().stream().map(
                                orderLineItemRequest ->  OrderLineItem.builder()
                                        .price(orderLineItemRequest.price())
                                        .skuCode(orderLineItemRequest.skuCode())
                                        .quantity(orderLineItemRequest.quantity())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .build();
    }
}
