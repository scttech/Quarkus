package com.scttech.quarkus.example.jdbc.rest;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

import com.scttech.quarkus.example.jdbc.model.Car;

@Path("/cars")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource {
	
	@Inject
	EntityManager entityManager;
	
	@GET
	public List<Car> getAll() {
		return entityManager.createNamedQuery("Cars.findAll", Car.class).getResultList();
	}
	
	@Path("/{id}")
	@GET
	public Car getById(@PathParam("id") Integer id) {
		return entityManager.find(Car.class, id);
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
