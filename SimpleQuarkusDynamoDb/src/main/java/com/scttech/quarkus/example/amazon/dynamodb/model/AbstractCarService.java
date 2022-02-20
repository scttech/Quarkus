package com.scttech.quarkus.example.amazon.dynamodb.model;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

public abstract class AbstractCarService {

	public static final String CAR_MAKE_COL = "make";
	public static final String CAR_MODEL_COL = "model";
	public static final String CAR_COLOR_COL = "color";
	public static final String CAR_VIN_COL = "vin";

	private static final String TABLE_NAME = "car";

	public static String getTableName() {
		return TABLE_NAME;
	}

	protected ScanRequest scanRequest() {
		return ScanRequest.builder().tableName(getTableName()).projectionExpression(CAR_MAKE_COL).build();
	}

	protected PutItemRequest putRequest(Car car) {
		Map<String, AttributeValue> item = new HashMap<>();
		item.put(CAR_MAKE_COL, AttributeValue.builder().s(car.getMake()).build());
		item.put(CAR_MODEL_COL, AttributeValue.builder().s(car.getModel()).build());
		item.put(CAR_COLOR_COL, AttributeValue.builder().s(car.getColor()).build());
		item.put(CAR_VIN_COL, AttributeValue.builder().s(car.getVin()).build());

		return PutItemRequest.builder().tableName(getTableName()).item(item).build();
	}

	protected GetItemRequest getRequest(String name) {
		Map<String, AttributeValue> key = new HashMap<>();
		key.put(CAR_MAKE_COL, AttributeValue.builder().s(name).build());

		return GetItemRequest.builder().tableName(getTableName()).key(key).projectionExpression(CAR_MAKE_COL).build();
	}

}
