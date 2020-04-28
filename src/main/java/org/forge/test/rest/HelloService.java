package org.forge.test.rest;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.Date;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;
import java.util.Arrays;

@Path("/greet")
public class HelloService {

	@GET
	@Produces("text/plain")
	public Response doGet() {
		MongoClient mongoClient = new MongoClient("172.30.47.7", 27017);
		boolean auth = mongoClient.getDB("vachubdb").authenticate("admin", "shiraadmin".toCharArray());
		return Response.ok("shira method doGet invoked " + auth + ", " + new Date()).build();
	}
}
