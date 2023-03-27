package org.sourceCode;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

//import static java.util.Collections.eq;
import static com.mongodb.client.model.Filters.eq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseHandler {
  /* Class variables */
  MongoDatabase database;
  String myCollection;
  String uri = "mongodb+srv://testuser:<password>@2522-test.rlthgky.mongodb.net/?retryWrites=true&w=majority";
  ExecutorService executor;
  /**
   * Constructor
   */
  public DatabaseHandler() {
    // connect
    ConnectionString connectionString = new ConnectionString("mongodb+srv://testuser:testuser123@cluster0.2gojpcl.mongodb.net/?retryWrites=true&w=majority");
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .serverApi(ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build())
        .build();
    MongoClient mongoClient = MongoClients.create(settings);
    this.database = mongoClient.getDatabase("ruby");
    this.myCollection = "savestate";
    try {
      this.database.createCollection(this.myCollection);
    } catch (Exception e) {
      System.err.println("Collection already exists");
    }
    this.executor = Executors.newFixedThreadPool(10);
  }

  /**
   * Writes new document to db collection.
   * @param key
   * @param value
   */
  public void put(String key, String value) {
    Document document = new Document();
    document.append(key, value);
    executor.execute(() -> database.getCollection("savestate").insertOne(document));
  }

  /**
   * Gets first Document in collection with key value.
   * @param key
   * @param value
   * @return Document
   */
  public Document get(String key, String value) {
    Document find = this.database.getCollection(this.myCollection).find(eq(key, value)).first();
    return find;
  }
  public static void main(String[] args) {
    DatabaseHandler db = new DatabaseHandler();

    // find
//    Document find = db.database.getCollection("students").find(eq("name", "Ram")).first();
//    db.put("hello", "world");
    Document found = db.get("hello", "world");

    System.out.println(found);
  }
}