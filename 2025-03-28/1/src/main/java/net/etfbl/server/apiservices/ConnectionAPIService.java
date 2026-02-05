package net.etfbl.server.apiservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.etfbl.models.dto.ConnectionParameters;
import net.etfbl.server.CountryServerThread;

@Path("/connections")
public class ConnectionAPIService {
	
	@GET
	@Path("/parameters")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getParameters() {
		return Response.ok(new ConnectionParameters(CountryServerThread.HOST, CountryServerThread.PORT)).build();
	}
}
