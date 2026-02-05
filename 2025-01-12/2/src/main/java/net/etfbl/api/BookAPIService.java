package net.etfbl.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.etfbl.books.BookService;
import net.etfbl.user.UserDAO;

@Path("/books")
public class BookAPIService {
	private final BookService bookService;

	public BookAPIService() {
		super();
		this.bookService = new BookService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{userid}")
	public Response getAll(@PathParam("userid") String userId) {
		new UserDAO().findAll().stream().forEach(System.out::println);
		return Response.ok(bookService.getAll(userId)).build();
	}
	
}
