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
		System.out.println("hello!!!3");
		mongoClient.getDatabaseNames().forEach(System.out::println);
		String s = mongoClient.getDatabaseNames().get(1);
		MongoDatabase database = mongoClient.getDatabase("myMongoDb");
		if (database != null) {
			MongoCollection collection = database.getCollection("customers");
			if (collection == null) {
			         database.createCollection("customers");
			} else {
                              System.out.println("collection already exist!!!");
			}
			for (String name : database.listCollectionNames()) {
				System.out.println(name);
			}
			Document document = new Document("title", "MongoDB")
			.append("description", "database")
			.append("likes", 100)
			.append("url", "http://www.tutorialspoint.com/mongodb/")
			.append("by", "tutorials point");

			//Inserting document into the collection
			collection.insertOne(document);
			System.out.println("Document inserted successfully");
		} else {
			System.out.println("database is null!!!");
		}
		return Response.ok("shira 3 method doGet invoked " + s + ", " + new Date()).build();
	}
}
