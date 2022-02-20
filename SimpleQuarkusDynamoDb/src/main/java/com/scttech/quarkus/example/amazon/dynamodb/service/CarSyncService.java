package com.scttech.quarkus.example.amazon.dynamodb.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.scttech.quarkus.example.amazon.dynamodb.model.AbstractCarService;
import com.scttech.quarkus.example.amazon.dynamodb.model.Car;

import io.quarkus.logging.Log;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

@ApplicationScoped
public class CarSyncService extends AbstractCarService {
	
	@Inject
	DynamoDbClient dynamoDb;
	
	@ConfigProperty(name = "app.aws.port")
	Optional<String> awsPort;
	
	public List<Car> findAll() {
		try {
			dynamoDb = DynamoDbClient.builder()
					.region(Region.US_EAST_1)
					.endpointOverride(URI.create("http://localhost:" + awsPort.orElse("8000")))
					.httpClientBuilder(UrlConnectionHttpClient.builder())
					.build();

			ScanRequest scanRequest = ScanRequest.builder()
					.tableName("car")
					.build();
			
			ScanResponse response = dynamoDb.scan(scanRequest);
			
			if(Log.isTraceEnabled()) {
				displayKeyValues(response);
			}
			
			return response.items().stream().map(Car::from).collect(Collectors.toList());
			
		} catch (Exception e) {
			Log.error("Unable to find cars: " + e.getMessage(), e);
		}
		
		return new ArrayList<>(); 
	}
	
	private void displayKeyValues(ScanResponse response) {
		for(Map<String, AttributeValue> item: response.items()) {
			Set<String> keys = item.keySet();
			for(String key : keys) {
                Log.trace("The key name is " + key);
                Log.trace("The value is " + item.get(key).s());
			}
		}
	}
	
	public List<Car> add(Car car){
		try {
			dynamoDb = DynamoDbClient.builder()
					.region(Region.US_EAST_1)
					.endpointOverride(URI.create("http://localhost:" + awsPort.orElse("8000")))
					.httpClientBuilder(UrlConnectionHttpClient.builder())
					.build();
			
			dynamoDb.putItem(putRequest(car));
		} catch (Exception e) {
			Log.error("Unable to add car: " + e.getMessage(), e);
		}
		
		return findAll();
	}
	
	public Car get(String name) {
		return Car.from(dynamoDb.getItem(getRequest(name)).item());
	}

}
