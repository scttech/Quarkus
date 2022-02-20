package com.scttech.quarkus.example.amazon.dynamodb.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import com.scttech.quarkus.example.amazon.dynamodb.model.Car;

@Path("/cars")
@RegisterRestClient(configKey="carClient")
/**
 * We can inject this instance into our test and use it to keep our tests clean
 * @author chris
 *
 */
public interface RestclientCarService {
	
	@GET
	@Path("/{id}")
	Car getById(@PathParam(value = "id") int id);
	
	@GET
	List<Car> getCars();
	
	@POST
	Car createCar(Car car);

}
