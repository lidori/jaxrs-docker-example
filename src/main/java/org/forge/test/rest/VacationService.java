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
import com.mongodb.client.MongoCursor;

import java.util.stream.StreamSupport;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;

@Path("/vacation")
public class VacationService {

	@GET
	@Produces("application/json")
	public Response getAllVacations() {
		MongoClientURI uri = new MongoClientURI("mongodb://admin:shiraadmin@172.30.47.7:27017");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("myMongoDb");
		if (database != null) {
			MongoCollection collection = database.getCollection("vacations");
			if (collection == null) {
			        System.out.println("collection vacations does not exist!!!");
			} else {
				MongoCursor<Document> cursor = collection.find().iterator();
				List<String> list = new ArrayList<String>(); 
				while(cursor.hasNext())
    					list.add(cursor.next().toJson());
        			return Response.ok().type("application/json").entity(list).build();
			}
		} else {
			System.out.println("database is null!!!");
		}
		System.out.println("Something is wrong!!!");
		return Response.status(Response.Status.BAD_REQUEST).entity("Something is wrong").build();
	}
	
	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public Response createVacation(Vacation vacation) {
		MongoClientURI uri = new MongoClientURI("mongodb://admin:shiraadmin@172.30.47.7:27017");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("myMongoDb");
		if (database != null) {
			MongoCollection collection = database.getCollection("vacations");
			if (collection == null) {
			  database.createCollection("vacations");
				collection = database.getCollection("vacations");
			} else {
        			System.out.println("collection vacations already exist!!!");
			}
			
			if (collection.find(eq("id", user.id)).first() == null) {
				Document document = new Document("id", vacation.id)
				.append("title", vacation.title)
				.append("description", vacation.description)
        			.append("type", vacation.type);

				//Inserting document into the collection
				collection.insertOne(document);
				System.out.println("Document inserted successfully");
			} else {
				System.out.println("Vacation already exists, document not inserted!!!");
			}
		} else {
			System.out.println("database is null!!!");
		}
		return Response.ok("handled vacation " + vacation.title).build();
	}
}
