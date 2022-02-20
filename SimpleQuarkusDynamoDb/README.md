# Overview

This project is an example of using AWS DynamoDB

# Setup

This assumes you have a docker instance running AWS DynamoDB on port 8000.

The unit tests are executed with a container and a dynamic port

# Cool Features

The project demonstrates the following cool features:

* Using @QuarkusMain to create the database before the application starts
* Using Testcontainers to start/stop the container when testing
* Create AWS DynamoDB table
* Put record to AWS DynamoDB table
* Get all records from AWS DynamoDB table

# Resource

[https://quarkiverse.github.io/quarkiverse-docs/quarkus-amazon-services/dev/amazon-dynamodb.html](https://quarkiverse.github.io/quarkiverse-docs/quarkus-amazon-services/dev/amazon-dynamodb.html)

[https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/dynamodb/src/main/java/com/example/dynamodb/CreateTable.java](https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/dynamodb/src/main/java/com/example/dynamodb/CreateTable.java)

[https://quarkus.io/guides/lifecycle](https://quarkus.io/guides/lifecycle)
