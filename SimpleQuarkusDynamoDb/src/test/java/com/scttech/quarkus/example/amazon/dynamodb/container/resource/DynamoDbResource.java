package com.scttech.quarkus.example.amazon.dynamodb.container.resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.utility.DockerImageName;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class DynamoDbResource implements QuarkusTestResourceLifecycleManager  {
		
	public static GenericContainer<?> dynamodb = new GenericContainer<>("amazon/dynamodb-local:latest")
			.withExposedPorts(8000)
			.withAccessToHost(true);

	@Override
	public Map<String, String> start() {
		dynamodb.start();
		return Collections.singletonMap("app.aws.port", dynamodb.getFirstMappedPort().toString());
	}

	@Override
	public void stop() {
		dynamodb.stop();		
	}

}
