package net.etfbl.apis;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.etfbl.exceptions.ConflictExcetpion;
import net.etfbl.exceptions.NotFoundException;
import net.etfbl.securities.SecuritiesEntity;
import net.etfbl.securities.SecuritiesService;
import net.etfbl.transactions.TransactionEntity;

@Path("/securities")
public class SecuritiesAPIService {
	private final SecuritiesService service;
	
	public SecuritiesAPIService() {
		this.service = new SecuritiesService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.ok(service.getAll()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sorted/{order}")
	public Response getAllSorted(@PathParam("order") String order) {
		return Response.ok(service.getAllSortedByLastTransactionDate(order)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getById(@PathParam("id") String id) {
		try {
			return Response.ok(service.getById(id)).build();
		} catch(NotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Securities not found - id = " + id)
					.build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(SecuritiesEntity request) {
		try {
			service.add(request);
			return Response.status(Response.Status.CREATED).entity(request).build();
		} catch(ConflictExcetpion e) {
			return Response.status(Response.Status.CONFLICT).entity("Invalid request!").build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{securitiesId}/transactions")
	public Response createTransaction(@PathParam("securitiesId") String securitiesId, TransactionEntity trans) {
		try {
			return Response.ok(service.addTransaction(securitiesId, trans)).build();
		} catch(NotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).entity("NOT FOUND SECURITIES").build();
		} catch(ConflictExcetpion e) {
			return Response.status(Response.Status.CONFLICT).entity("NOT VALID REQUEST").build();
		}
	}

}
