package com.scttech.quarkus.example.jdbc.rest;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.arjuna.ats.internal.arjuna.objectstore.jdbc.drivers.postgres_driver;
import com.scttech.quarkus.example.jdbc.model.Car;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;


@QuarkusTest
class CarResourceTest {
	
	@Inject
	@RestClient
	RestclientCarService carClient;
	
	@ConfigProperty(name = "quarkus.http.test-port")
	Integer assignedPort;
	
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
	
	@Test
	void testPostAndGetCar() {
		
		Car car = new Car();
		car.setModel("Mazda");
		
		car = carClient.createCar(car);	
		
		given()
		.when()
		.contentType(MediaType.APPLICATION_JSON)		
		.get("/cars/" + car.getId())
		.then()
		.statusCode(Status.OK.getStatusCode())
		.and()
		.body("model", equalTo("Mazda"));
		
	}	

}
