package com.fazil.ms.orderservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fazil.ms.orderservice.controller.OrderController;
import com.fazil.ms.orderservice.dto.OrderRequest;
import com.fazil.ms.orderservice.dto.OrderResponse;
import com.fazil.ms.orderservice.entity.Order;
import com.fazil.ms.orderservice.mapper.OrderMapper;
import com.fazil.ms.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


@Slf4j
class OrderServiceControllerUnitTests {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setupBeforeAll() {
        MockitoAnnotations.openMocks(this);
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Should Place order")
    public void shouldPlaceOrder() throws JsonProcessingException {
        log.info("should place order test started ");
        OrderRequest orderRequest = objectMapper.readValue(getOrderRequest(), OrderRequest.class);
        Order order = OrderMapper.toOrder(orderRequest);
        order.setId(Long.valueOf(1));
        Mockito.when(orderService.placeOrder(Mockito.any())).thenReturn(OrderMapper.toOrderResponse(order));
        OrderResponse orderResponse = orderController.placeOrder(orderRequest);
        Assertions.assertNotNull(orderResponse.id());
        Assertions.assertNotNull(orderResponse.orderLineItems());
    }

    private static String getOrderRequest() {
        return """
                 {
                    "orderLineItems": [
                        {
                            "skuCode": "iphone_15pro_512",
                                "price": 4599.00,
                                "quantity": 2
                        }
                    ]
                }
                """;
    }
}
