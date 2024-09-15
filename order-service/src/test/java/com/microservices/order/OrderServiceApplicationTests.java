package com.microservices.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
import org.hamcrest.Matchers;
import static org.hamcrest.MatcherAssert.*;

import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class OrderServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

	@LocalServerPort
	private int port;

	
	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
	
	static{
		mySQLContainer.start();
	}

	@Test
	void shouldSubmitOrder(){
		String submitOrderJson = """
				{
				"skuCode": "Iphone 16",
				"price": 999,
				"quantity": 1
				}
				""";
		var responseBodyString = RestAssured.given().contentType("application/json")
								.body(submitOrderJson)
								.when().post("/api/order").then()
								.log().all()
								.statusCode(201)
								.extract().body().toString();
		assertThat(responseBodyString, Matchers.is("Order placed successfully"));
	}


}
