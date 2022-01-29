package com.scttech.quarkus.example.jdbc.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.scttech.quarkus.example.jdbc.model.Car;

@Path("cars")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource {
	
	@Inject
	EntityManager entityManager;
	
	@GET
	public List<Car> get() {
		return entityManager.createNamedQuery("Cars.findAll", Car.class).getResultList();
	}
	
	@POST
	@Transactional
	public Response create(Car  car) {
		if(car.getId() != 0) {
			throw new WebApplicationException("Id should not be set on request.", Status.BAD_REQUEST);
		}
		
		entityManager.persist(car);
		
		return Response.ok(car).status(Status.CREATED).build();
	}

}
