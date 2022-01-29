package com.scttech.quarkus.example.jdbc.container.resource;

import java.util.Collections;
import java.util.Map;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.utility.DockerImageName;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class MariaDbResource implements QuarkusTestResourceLifecycleManager  {
		
	static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:latest")
			.withDatabaseName("hibernate_orm_test")
			.withUsername("junit")
			.withPassword("my_cool_secret");

	@Override
	public Map<String, String> start() {
		mariadb.start();
				
		return Collections.singletonMap("quarkus.datasource.url", mariadb.getJdbcUrl());
	}

	@Override
	public void stop() {
		mariadb.stop();		
	}

}
