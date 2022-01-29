# Overview

This code is a sample using JDBC in Quarkus.  

## Cool Features

This project has the following cool features

* Use Testcontainers to leverage a Docker image of MariaDB for testing
* 

# Resources
This code was based off the following:

[hibernate-orm-quickstart](https://github.com/quarkusio/quarkus-quickstarts/tree/main/hibernate-orm-quickstart)

[Quarkus and Test Containers](https://www.morling.dev/blog/quarkus-and-testcontainers/)


# Troubleshooting

## Problem: Unable to find message body reader for type

### Solution: Update pom.xml to include jackson or jax-b
The pom.xml was missing a dependency on jackson and resolved by adding the following


	<dependency>
		<groupId>io.quarkus</groupId>
		<artifactId>quarkus-resteasy-jackson</artifactId>
	</dependency>


