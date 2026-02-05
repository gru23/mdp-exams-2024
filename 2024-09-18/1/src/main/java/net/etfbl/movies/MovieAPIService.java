package net.etfbl.movies;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.etfbl.exceptions.NotFoundException;

@Path("/movies")
public class MovieAPIService {
	private final MovieService service;
	
	public MovieAPIService() {
		this.service = new MovieService();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{title}")
	public Response getByTitle(@PathParam("title") String title) {
		try {
			return Response.ok(service.getByTitle(title)).build();
		} catch (NotFoundException e) {
			e.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/genres/{genre}")
	public Response getAllByGenre(@PathParam("genre") String genre) {
		return Response.ok(service.getAllByGenre(genre)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/genres")
	public Response getAllGenres() {
		return Response.ok(service.getAllGenres()).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(MovieEntity movie) {
		// moze validacija polja kao u roku 31.01.2024. sto sam uradio
		return Response.status(Response.Status.CREATED).entity(service.create(movie)).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id")Integer id) {
		try {
			service.deleteById(id);
			return Response.noContent().build();
		} catch (NotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
}
