package com.scttech.quarkus.example.amazon.dynamodb.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.scttech.quarkus.example.amazon.dynamodb.model.Car;
import com.scttech.quarkus.example.amazon.dynamodb.service.CarSyncService;

@Path("cars")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarRestImpl {
	
	@Inject
	CarSyncService carService;
	
	@GET
	public List<Car> getAll() {
		return carService.findAll();
	}
	
	@GET
	@Path("/{name}")
	public Car getSingle(@PathParam("name") String name) {
		return carService.get(name);
	}
	
    @POST
    public Car add(Car car) {
    	carService.add(car);
        return car;
    }	

}
