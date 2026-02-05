package net.etfbl.api;

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.etfbl.books.BookEntity;
import net.etfbl.books.BookService;
import net.etfbl.exceptions.BadRequestException;
import net.etfbl.exceptions.NotFoundException;

@Path("/books")
public class BookApiService {
	private final BookService bookService;
	
	public BookApiService() {
		this.bookService = new BookService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.ok(bookService.getAll()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id-and-title")
	public Response getAllWithIdAndTitle() {
		return Response.ok(bookService.getAllWithIdAndTitle()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/publishers/{name}")
	public Response getAllByPublisherName(@PathParam("name") String name) {
		return Response.ok(bookService.getAllByPublisherName(name)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getById(@PathParam("id") String id) {
		Optional<BookEntity> book = bookService.getById(id);
		if(book.isEmpty())
			return Response.status(Response.Status.NOT_FOUND).build();
		return Response.ok(book.get())
				.entity("Book with id " + id + " not found")
				.build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(BookEntity book) {
		try {
			BookEntity createdBook = bookService.add(book);
			return Response.status(Response.Status.CREATED).entity(createdBook).build();
		} catch(BadRequestException e) {
			return Response.status(Response.Status.BAD_REQUEST)
			.entity("Invalid request, fileds can not be null!")
			.build();
		}
		//ne da mi se, ali moze se dodati i da li je duplikat (409 - Conflict)
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {
		try {
			bookService.delete(id);
			return Response.noContent().build();
		} catch(NotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Book with id " + id + " not found")
					.build();
		}
	}
}
