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

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Path("/greet")
public class HelloService {

	@GET
	@Produces("text/plain")
	public Response doGet() {
		MongoClientURI uri = new MongoClientURI("mongodb://admin:shiraadmin@172.30.47.7:27017");
		MongoClient mongoClient = new MongoClient(uri);
		mongoClient.getDatabaseNames().forEach(System.out::println);
		String s = mongoClient.getDatabaseNames().get(0);
		MongoDatabase database = mongoClient.getDatabase("myMongoDb");
		System.out.println("database is " + database);
		database.createCollection("customers");
		for (String name : database.listCollectionNames()) {
			System.out.println(name);
		}
		return Response.ok("shira 1 method doGet invoked " + s + ", " + new Date()).build();
	}
}
