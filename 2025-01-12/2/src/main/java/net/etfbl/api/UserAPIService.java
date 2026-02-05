package net.etfbl.api;


import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.etfbl.orders.OrderRequest;
import net.etfbl.orders.OrderService;
import net.etfbl.user.UserService;

@Path("/users")
public class UserAPIService {
	private final UserService userService;
	private final OrderService orderService;
	
	public UserAPIService() {
		this.userService = new UserService();
		this.orderService = new OrderService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.ok(userService.getAll()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/orders")
	public void order(@Valid OrderRequest request) {
		orderService.order(request);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{userid}/orders")
	public Response getAllOrdersByUserId(@PathParam("userid") String userId) {
		return Response.ok(orderService.getAllOrderedBooksByUserId(userId)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/costs")
	public Response getAllCostsByUserId(@PathParam("id") String id) {
		return Response.ok(userService.getCosts(id)).build();
	}
}
