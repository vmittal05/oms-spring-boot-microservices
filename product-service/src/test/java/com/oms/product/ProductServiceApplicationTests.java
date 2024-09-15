package com.oms.product;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	//Automatically inject the mongodb port
	@ServiceConnection
	static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:latest");

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static{
		mongoDbContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
				{
				"name": "Iphone 16",
				"description": "Product Description",
				"price": 999
				}
				""";
		RestAssured.given().contentType("application/json").body(requestBody)
				.when().post("/api/products").then().statusCode(201)
				.body("id",Matchers.notNullValue())
				.body("name", Matchers.equalTo("Iphone 16"))
				.body("description", Matchers.equalTo("Product Description"))
				.body("price", Matchers.equalTo(999));
	}

}
