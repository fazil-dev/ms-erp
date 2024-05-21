package com.fazil.ms.inventoryservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fazil.ms.inventoryservice.entity.Inventory;
import com.fazil.ms.inventoryservice.repository.InventoryRepository;
import com.fazil.ms.inventoryservice.resource.InventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@SpringBootTest
@Testcontainers
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class InventoryServiceApplicationTests {

	@Container
	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer(DockerImageName.parse("mysql"));

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private InventoryRepository inventoryRepository;

	private static ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@DisplayName("Should Create Inventory")
	void shouldCreateInventory() throws Exception {
		List<Inventory> inventoryList =  inventoryRepository.findAll();
		log.info("shouldCreateInventory test method started");
		 MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/inventory")
						 .contentType(MediaType.APPLICATION_JSON)
				 .content(getInventoryRequest()))
				 .andExpect(MockMvcResultMatchers.status().isCreated())
				 .andReturn();

	}

	@Test
	@DisplayName("Get Inventory with one record")
	void shouldGetOneInventory() throws Exception {
		log.info("shouldGetOneInventory test method started");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/inventory")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getInventoryRequest()));

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inventory")
						.param("skuCode","IPhone1005")
				).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		List<InventoryResponse> inventoryResponses = List.of(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), InventoryResponse[].class));
		Assertions.assertEquals(1, inventoryResponses.size());

		log.info(mvcResult.getResponse().getContentAsString());
	}

	private String getInventoryRequest() {
		return """
				{
				    "skuCode": "IPhone1005",
				    "quantity": 5
				}
				""";
	}
}
