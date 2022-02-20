package com.scttech.quarkus.example.amazon.dynamodb;

import java.util.Map;
import java.util.Optional;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.scttech.quarkus.example.amazon.dynamodb.setup.CreateDatabase;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * Using @QuarkusMain allows us to call a method to populate the database
 * @author chris
 *
 */
@QuarkusMain
public class Main {
	
	public static void main(String ...args) {
		Log.info("Running main method");
		createTables();
		Quarkus.run(args);
	}
	
	private static void createTables() {
		CreateDatabase cdb = new CreateDatabase();
		Map<String, String> createResult = cdb.createTable(8000);
		
		if(!createResult.get("errors").isEmpty()) {
			Log.error("Found error: " + createResult.get("errors"));
		} else {
			Log.info("Tables successfully created");
		}
	}

}
