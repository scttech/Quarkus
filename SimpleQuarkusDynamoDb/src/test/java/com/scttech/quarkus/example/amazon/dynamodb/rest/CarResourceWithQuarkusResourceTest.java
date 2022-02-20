package com.scttech.quarkus.example.amazon.dynamodb.rest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.spi.HttpResponseCodes;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.scttech.quarkus.example.amazon.dynamodb.container.resource.DynamoDbResource;
import com.scttech.quarkus.example.amazon.dynamodb.model.Car;
import com.scttech.quarkus.example.amazon.dynamodb.setup.CreateDatabase;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@QuarkusTest
@QuarkusTestResource(DynamoDbResource.class)
/**
 * This class uses an example of managing the container via @QuarkusTestResource
 * 
 * The drawback with this current setup is that the container is not restarted for each test. Therefore,
 * we put the creation of the database in a method annotated with @BeforeAll
 * 
 * On the other hand, the details of the container are hidden in DynamoDbResource so that could be a positive
 * or negative depending on your perspective
 * 
 * @author chris
 *
 */
class CarResourceWithQuarkusResourceTest {
	
	@Inject
	@RestClient
	RestclientCarService carClient;
	
	@ConfigProperty(name="quarkus.rest-client.carClient.url")
	String url;
	
	
	@BeforeAll
	static void beforeAll() {
		
		CreateDatabase cdb = new CreateDatabase();
		Map<String, String> createResult = cdb.createTable(DynamoDbResource.dynamodb.getFirstMappedPort());
		
		assertEquals("", createResult.get("errors"));
		assertFalse(createResult.get("tableName").isEmpty());		
		
	}

	@Test
	void testGetEmpty() {
			
		given()
		.when()
		.get("/cars")
		.then()
		.statusCode(200)
		.body(is("[]"));
	}
	
	@Test
	void testPostCar() {

		Car car = new Car();
		car.setMake("Toyota");
		car.setModel("Camry");
		car.setColor("Green");
		car.setVin("123456");
		
		Car car2 = carClient.createCar(car);
		
		assertEquals(car.getVin(), car2.getVin());
		
		List<Car> cars = new ArrayList<>();
		try {
		    cars = carClient.getCars();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		assertEquals(1, cars.size());
				
	}

}
