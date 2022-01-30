# Overview

This code is a sample using REST and Swagger in Quarkus.  

## Cool Features

This project has the following cool features
* Use the Resteasy client for unit testing
* Download the swagger from [http://localhost:8080/swagger](http://localhost:8080/swagger)
* View the Swagger UI from [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui)

# Resources
This code was based off the following:

[hibernate-orm-quickstart](https://github.com/quarkusio/quarkus-quickstarts/tree/main/hibernate-orm-quickstart)

[Quarkus and Test Containers](https://www.morling.dev/blog/quarkus-and-testcontainers/)

[Quarkus Datasource](https://quarkus.io/guides/datasource)

[Quarkus Rest Client](https://quarkus.io/guides/rest-client)

# Running the Application

	mvn compile quarkus:dev

Or, you can execute the unit tests

# 

# Troubleshooting

# Problem - java.lang.RuntimeException: java.lang.RuntimeException:io.quarkus.builder.BuildException: Build failure: Build failed due to errors

In the stack trace we see:

	[error]: Build step io.quarkus.arc.deployment.ArcProcessor#validate threw an exception: javax.enterprise.inject.spi.DeploymentException:
	Caused by: javax.enterprise.inject.UnsatisfiedResolutionException: Unsatisfied dependency for type com.scttech.quarkus.example.jdbc.rest.RestclientCarService and qualifiers [@Default]
	- java member: com.scttech.quarkus.example.jdbc.rest.CarResourceTest#carClient
	- declared on CLASS bean [types=[com.scttech.quarkus.example.jdbc.rest.CarResourceTest, java.lang.Object], qualifiers=[@Default, @Any], target=com.scttech.quarkus.example.jdbc.rest.CarResourceTest]
	The following beans match by type, but none have matching qualifiers:
		- Bean [class=com.scttech.quarkus.example.jdbc.rest.RestclientCarService, qualifiers=


## Solution

Forgot to include the @RestClient annotation in CarResourceTest.java


	@Inject
	@RestClient
	RestclientCarService carClient;
