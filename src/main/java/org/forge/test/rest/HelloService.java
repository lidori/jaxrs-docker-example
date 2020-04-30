package org.forge.test.rest;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;
import java.util.Arrays;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

import java.util.stream.StreamSupport;
import java.util.stream.Collectors;

@Path("/greet")
public class HelloService {

	@GET
	@Produces("application/json")
	public Response doGet() {
		MongoClientURI uri = new MongoClientURI("mongodb://admin:shiraadmin@172.30.47.7:27017");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("myMongoDb");
		if (database != null) {
			MongoCollection collection = database.getCollection("users");
			if (collection == null) {
			         System.out.println("collection users does not exist!!!");
			} else {
                             Response.ok().type("application/json").entity(StreamSupport.stream(collection.find().spliterator(), false)
        			.map(Document::toJson)
        			.collect(Collectors.joining(", ", "[", "]"))).build();
			}
		} else {
			System.out.println("database is null!!!");
		}

		//return Response.ok("shira 3 method doGet invoked " + s + ", " + new Date()).build();
		return Response.status(Status.BAD_REQUEST).entity("No users").build();
	}
	
	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public Response createUser(User user) {
		MongoClientURI uri = new MongoClientURI("mongodb://admin:shiraadmin@172.30.47.7:27017");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("myMongoDb");
		if (database != null) {
			MongoCollection collection = database.getCollection("users");
			if (collection == null) {
			        database.createCollection("users");
				collection = database.getCollection("users");
			} else {
                              System.out.println("collection users already exist!!!");
			}
			
			if (collection.find(eq("id", user.id)).first() == null) {
				Document document = new Document("id", user.id)
				.append("username", user.username)
				.append("image", user.image);

				//Inserting document into the collection
				collection.insertOne(document);
				System.out.println("Document inserted successfully");
			} else {
				System.out.println("User already exists, document not inserted!!!");
			}
		} else {
			System.out.println("database is null!!!");
		}
		return Response.ok("handled user " + user.username).build();
	}
}
