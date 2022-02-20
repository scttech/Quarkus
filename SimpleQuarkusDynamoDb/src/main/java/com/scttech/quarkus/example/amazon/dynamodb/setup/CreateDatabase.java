package com.scttech.quarkus.example.amazon.dynamodb.setup;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.scttech.quarkus.example.amazon.dynamodb.model.AbstractCarService;

import io.quarkus.logging.Log;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

public class CreateDatabase {
	
	public Map<String, String> createTable() {
		return createTable(8000);
	}
	
	public Map<String, String> createTable(int port) {
		
		Map<String, String> result = new HashMap<>();
		result.put("tableName", "");
		result.put("errors", "");
		
		String tableName = AbstractCarService.getTableName();
		
		DynamoDbClient client = DynamoDbClient.builder()
				.region(Region.US_EAST_1)
				.endpointOverride(URI.create("http://localhost:" + port))
				.httpClientBuilder(UrlConnectionHttpClient.builder())
				.build();
		
		CreateTableRequest request = CreateTableRequest.builder()
				.attributeDefinitions(AttributeDefinition.builder()
						.attributeName("vin")
						.attributeType(ScalarAttributeType.S)
						.build())
				.keySchema(KeySchemaElement.builder()
						.attributeName("vin")
						.keyType(KeyType.HASH)
						.build())
				.provisionedThroughput(ProvisionedThroughput.builder()
						.readCapacityUnits(10L)
						.writeCapacityUnits(10L)
						.build())
				.tableName(tableName)
				.build();
		
		DynamoDbWaiter dbWaiter = client.waiter();
		
		String newTable = "";
		
	       try {
	            CreateTableResponse response = client.createTable(request);
	            DescribeTableRequest tableRequest = DescribeTableRequest.builder()
	                    .tableName(tableName)
	                    .build();

	            // Wait until the Amazon DynamoDB table is created
	            WaiterResponse<DescribeTableResponse> waiterResponse =  dbWaiter.waitUntilTableExists(tableRequest);
	            waiterResponse.matched().response().ifPresent(System.out::println);

	            newTable = response.tableDescription().tableName();
	            result.put("tableName", newTable);
	            
	            return result;

	        } catch (DynamoDbException e) {
	            Log.error(e.getMessage(),e);
	            result.put("errors", e.getMessage());
	        }
	       
	       return result;		

	}

}
