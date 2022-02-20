package com.scttech.quarkus.example.amazon.dynamodb.rest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.scttech.quarkus.example.amazon.dynamodb.container.resource.DynamoDbResource;
import com.scttech.quarkus.example.amazon.dynamodb.setup.CreateDatabase;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Testcontainers
/**
 * These tests do no use a QuarkusTestResource and instead use the standard
 * Testcontainers setup.
 * 
 * @author chris
 *
 */
class CarResourceWithSharedContainerTest {

	@Inject
	@RestClient
	RestclientCarService carClient;

	@Container
	public static GenericContainer<?> dynamodb = new GenericContainer<>("amazon/dynamodb-local:latest")
			.withExposedPorts(8000).withAccessToHost(true);

	@BeforeAll
	static void beforeEach() {
		
		//Don't run these tests, instead use the CarResourceWithQuarkusResourceTest
		Assumptions.assumeTrue(false);

		CreateDatabase cdb = new CreateDatabase();
		Map<String, String> createResult = cdb.createTable(DynamoDbResource.dynamodb.getFirstMappedPort());

		assertEquals("", createResult.get("errors"));
		assertFalse(createResult.get("tableName").isEmpty());

	}

	@Test
	void testGetEmpty() {

		given().when().get("/cars").then().statusCode(200).body(is("[]"));
	}

	@Test
	void testRetrieveCars() {

		given().when().get("/cars").then().statusCode(200).body(is("[]"));
	}

}
