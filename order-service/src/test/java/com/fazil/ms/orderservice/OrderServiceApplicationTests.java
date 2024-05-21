package com.fazil.ms.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fazil.ms.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.stream.Stream;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Testcontainers
@AutoConfigureMockMvc
@Slf4j
class OrderServiceApplicationTests {

	@Container
	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer(MySQLContainer.NAME);

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private OrderRepository orderRepository;

	static ObjectMapper objectMapper = new ObjectMapper();


	@ParameterizedTest
	@MethodSource("getOrderRequest")
	@DisplayName("Should place order")
	public void shouldPlaceOrder(String orderJsonString, int paramCount) throws Exception {
		log.info("Should place order Test running for {} and param count {}.", orderJsonString, paramCount);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content(orderJsonString)
		)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn();
		Assertions.assertEquals(paramCount, orderRepository.findAll().size());

	}

	private static Stream<Arguments> getOrderRequest() {
		return Stream.of(
				Arguments.of("""
     			{
					"orderLineItems": [
						{
							"skuCode": "iphone_15pro_512",
								"price": 4599.00,
								"quantity": 2
						}
					]
				}
				""", 1),
				Arguments.of("""
     			{
					"orderLineItems": [
						{
							"skuCode": "iphone_15pro_256",
								"price": 4599.00,
								"quantity": 4
						}
					]
				}
				""", 2)
				);
	}

	private static Stream<String> getOrderRequestOnlyStringParam() {
		return Stream.of(
				"""
     			{
					"orderLineItems": [
						{
							"skuCode": "iphone_15pro_512",
								"price": 4599.00,
								"quantity": 2
						}
					]
				}
				""",
				"""
     			{
					"orderLineItems": [
						{
							"skuCode": "iphone_15pro_256",
								"price": 4599.00,
								"quantity": 4
						}
					]
				}
				"""
		);
	}
}
