package org.project.Datastate;

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
  ExecutorService executor;

  /**
   * Constructs new DatabaseHandler singleton
   */
  private DatabaseHandler() {
    // connect
    String password = "testuser123";
    String user = "testuser";
    String uri = "mongodb+srv://" + user + ":" + password + "@cluster0.2gojpcl.mongodb.net/?retryWrites=true&w=majority";
    ConnectionString connectionString = new ConnectionString(uri);
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .serverApi(ServerApi.builder()
        .version(ServerApiVersion.V1)
        .build())
        .build();

    try (MongoClient mongoClient = MongoClients.create(settings)) {
      String dbName = "ruby";
      this.database = mongoClient.getDatabase(dbName);
    }

    // test if collection exists
    try {
      this.database.createCollection(this.myCollection);
    } catch (Exception e) {
      System.err.println("Collection already exists");
    }
    this.executor = Executors.newFixedThreadPool(10);
  }

  /**
   * Gets instance of DatabaseHandler.
   *
   * @return instance of DatabaseHandler
   */
  public static synchronized DatabaseHandler getInstance() {
    if (instance == null) {
      instance = new DatabaseHandler();
    }
    return instance;
  }


  /**
   * Writes new Document to collection.
   *
   * @param document - Document to be written
   */
  public void put(Document document) {
    database.getCollection("savestate").insertOne(document);
  }

  /**
   * Gets first Document in collection with key value.
   *
   * @param key,   existing key of document
   * @param value, existing value of kv pair in document
   * @return Document
   */
  public Document get(String key, String value) {
    return this.database.getCollection(this.myCollection).find(eq(key, value)).first();
  }

  /**
   * Updates existing document in Collection
   *
   * @param key,   existing key of document
   * @param value, existing value of kv pair in document
   * @param doc,   new document of new key value pairs
   */
  public void update(String key, String value, Document doc) {
    this.database.getCollection(this.myCollection).updateOne(eq(key, value),
        new Document("$set", doc));
  }
}