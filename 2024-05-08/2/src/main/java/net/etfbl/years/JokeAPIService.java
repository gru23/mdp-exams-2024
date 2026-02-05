package net.etfbl.years;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.etfbl.exceptions.JokeAPIException;

@Path("/jokes")
public class JokeAPIService {
	private final JokeService service;
	
	public JokeAPIService() {
		this.service = new JokeService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.ok(service.getAllIds()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/descending")
	public Response getAllDescending() {
		return Response.ok(service.getAllIdsDescending()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/greater")
	public Response getAllGreaterThen(@QueryParam("id") String id) {
		return Response.ok(service.getAllIdsGreaterThen(id)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getById(@PathParam("id") String id) {
		try {
			return Response.ok(service.getJokeById(id)).build();
		} catch(JokeAPIException e) {
			e.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).entity("Joke with id " + id + "does not exist!").build();
		}
	}
	
	@POST
	@Path("/start/{n}")
	public Response start(@PathParam("n") String n) {
		try {
			service.startApp(Integer.parseInt(n));
			return Response.ok().build();
		} catch (JokeAPIException e) {
			System.err.println(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}
