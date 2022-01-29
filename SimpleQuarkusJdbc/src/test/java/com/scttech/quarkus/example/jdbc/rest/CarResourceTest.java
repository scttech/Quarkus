package com.scttech.quarkus.example.jdbc.rest;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.scttech.quarkus.example.jdbc.container.resource.MariaDbResource;
import com.scttech.quarkus.example.jdbc.model.Car;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;


@QuarkusTest
@QuarkusTestResource(MariaDbResource.class)
class CarResourceTest {
	
	@Test
	void testPostCar() {
		Car car = new Car();
		car.setModel("Toyota");
		
		given()
		.when()
		.body(car)
		.contentType(MediaType.APPLICATION_JSON)
		.post("/cars")
		.then()
		.statusCode(Status.CREATED.getStatusCode())
		.and()
		.body("id", equalTo(1))
		.and()
		.body("model", equalTo("Toyota"));
		
	}

}
