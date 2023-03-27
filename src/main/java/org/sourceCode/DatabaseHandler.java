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

/**
 * Handles Database interactions with MongoDB. DatabaseHandler implements
 * Singleton design pattern.
 *
 * @author Greg Song
 * @version 2023-03-27
 */
public class DatabaseHandler {
  /* Class variables */
  private static DatabaseHandler instance;
  private final MongoDatabase database;
  private final String myCollection = "savestate";
  private final String user = "testuser";
  private final String password = "testuser123";
  private final String uri = "mongodb+srv://" + this.user + ":" + this.password + "@cluster0.2gojpcl.mongodb.net/?retryWrites=true&w=majority";
  private final String dbName = "ruby";
  ExecutorService executor;

  /**
   * Constructs new DatabaseHandler
   */
  private DatabaseHandler() {
    // connect
    ConnectionString connectionString = new ConnectionString(this.uri);
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .serverApi(ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build())
        .build();
    MongoClient mongoClient = MongoClients.create(settings);
    this.database = mongoClient.getDatabase(this.dbName);
    // test if collection exists
    try {
      this.database.createCollection(this.myCollection);
    } catch (Exception e) {
      System.err.println("Collection already exists");
    }
    // --
    this.executor = Executors.newFixedThreadPool(10);
  }

  /**
   * Gets instance of DatabaseHandler
   * @return instance of DatabaseHandler
   */
  public static synchronized DatabaseHandler getInstance() {
    if (instance == null) {
      instance = new DatabaseHandler();
    }
    return instance;
  }

//  public void put(String key, String value) {
//    Document document = new Document();
//    document.append(key, value);
//    executor.execute(() -> database.getCollection("savestate").insertOne(document));
//  }

  /**
   * Writes new Document to collection.
   * @param document
   */
  public void put(Document document) {
    database.getCollection("savestate").insertOne(document);
  }

  /**
   * Gets first Document in collection with key value.
   * @param key
   * @param value
   * @return Document
   * TODO: need to make this async/take a callback
   */
  public Document get(String key, String value) {
    Document find = this.database.getCollection(this.myCollection).find(eq(key, value)).first();
    return find;
  }

  /**
   * Updates existing document in Collection
   * @param key, existing key of document
   * @param value, existing value of kv pair in document
   * @param doc, new document of new key value pairs
   */
  public void update(String key, String value, Document doc) {
    this.database.getCollection(this.myCollection).updateOne(eq(key, value), new Document("$set", doc));
  }

//  public boolean userExists(String username) {
//    Document user = this.database.getCollection("users").find(eq("username", username)).first();
//    return user != null;
//  }


  public static void main(String[] args) {
    DatabaseHandler db = new DatabaseHandler();

    // find
//    Document find = db.database.getCollection("students").find(eq("name", "Ram")).first();
//    db.put("hello", "world");
    Document found = db.get("hello", "world");

    System.out.println(found);
  }
}