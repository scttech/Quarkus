package com.scttech.quarkus.example.jdbc.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import com.scttech.quarkus.example.jdbc.model.Car;

@Path("/cars")
@RegisterRestClient(configKey="carClient")
public interface RestclientCarService {
	
	@GET
	@Path("/{id}")
	Car getById(@PathParam(value = "id") int id);
	
	@POST
	Car createCar(Car car);

}
