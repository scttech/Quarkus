package com.scttech.quarkus.example.amazon.dynamodb.model;

import java.util.Map;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@RegisterForReflection
public class Car {
	
	private String make;
	private String model;
	private String color;
	private String vin;
		
	public static Car from(Map<String, AttributeValue> item) {
		Car car = new Car();
		if (item != null && !item.isEmpty()) {
			car.setMake(item.get(AbstractCarService.CAR_MAKE_COL).s());
			car.setModel(item.get(AbstractCarService.CAR_MODEL_COL).s());
			car.setColor(item.get(AbstractCarService.CAR_COLOR_COL).s());
			car.setVin(item.get(AbstractCarService.CAR_VIN_COL).s());
		}
		
		return car;
	}
	
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	
}
