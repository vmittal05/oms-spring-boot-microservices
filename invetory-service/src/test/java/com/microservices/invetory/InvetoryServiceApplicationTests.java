package com.microservices.invetory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import io.restassured.RestAssured;

// @Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InvetoryServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mysqlContainer = new MySQLContainer("mysql:8.3.0");

	@LocalServerPort
	private int port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mysqlContainer.start();
	}
	
	@Test
	void shouldReadInventory() {
		var respone = RestAssured.given().when().get("/api/inventory?skuCode=IPhone%2014&quantity=10")
						.then().log().all()
						.statusCode(200).extract().response().as(Boolean.class);
		assertTrue(respone);

		var negativeResponse = RestAssured.given().when().get("/api/inventory?skuCode=IPhone%2014&quantity=10000")
						.then().log().all()
						.statusCode(200).extract().response().as(Boolean.class);
						
		assertFalse(negativeResponse);

	}

	}
