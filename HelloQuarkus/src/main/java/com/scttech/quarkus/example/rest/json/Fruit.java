package com.scttech.quarkus.example.rest.json;

public class Fruit {
	
	public String name;
	public String description;
	
	public Fruit() {}
	
	public Fruit(String name, String description) {
		this.name = name;
		this.description = description;
	}

}