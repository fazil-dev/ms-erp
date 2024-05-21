package com.fazil.ms.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fazil.ms.productservice.dto.ProductRequest;
import com.fazil.ms.productservice.dto.ProductResponse;
import com.fazil.ms.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Slf4j
class ProductServiceApplicationTests {

	@Container
	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo");

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ProductRepository productRepository;

	static ObjectMapper objectMapper = new ObjectMapper();

	//	Deprecated this because added @ServiceConnection annotation in mongodb container
	/*
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}
	*/

	@BeforeAll
	static void setup() {
		log.info("Started ProductServiceApplicationTests setup");
	}

	@AfterAll
	static void complete() {
		log.info("Finished ProductServiceApplicationTests setup");
	}

	@Test
	void shouldCreateProduct() throws Exception {

		MvcResult responseMvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
									.contentType(MediaType.APPLICATION_JSON)
									.content(objectMapper.writeValueAsString(getProductRequest())))
				.andExpect(status().isCreated())
				.andReturn();
		String responseString = responseMvcResult.getResponse().getContentAsString();
		Assertions.assertEquals(1, productRepository.findAll().size());
		ProductResponse productResponse = objectMapper.readValue(responseString, ProductResponse.class);
		log.info("response string {}. ", responseString);
	}

	@Test
	void shouldFindAllHaveRecords() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
				.andReturn().getResponse();
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Apple Iphone 11")
				.description("Apple Iphone 512GB Memory")
				.price(BigDecimal.valueOf(5299.00))
				.build();
	}

}
