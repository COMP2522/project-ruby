package org.project.DataState;

import org.bson.Document;
import org.json.simple.*;

import java.io.*;
import java.net.Socket;

/**
 * Class that handles GET Requests received by Server.
 *
 * @author Greg Song
 * @version 2023-03-27
 */
public class GetRequestHandler implements Runnable {
  private final DatabaseHandler databaseHandler;
  private final Socket socket;
  private final JSONObject obj;

  /**
   * Constructs a new GetRequestHandler
   * @param socket, the client socket
   * @param obj, a JSONObject containing request data
   */
  public GetRequestHandler(Socket socket, JSONObject obj) {
    this.socket = socket;
    this.databaseHandler = DatabaseHandler.getInstance();
    this.obj = obj;
  }

  /**
   * Sends response to client.
   * @param message - Message to be sent to Client
   */
  public void sendResponse(String message) throws Exception {
    OutputStream outputStream = this.socket.getOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    String res = createJSONRes(message);

    // Write data to the ObjectOutputStream
    objectOutputStream.writeObject(res);

    // Flush to ensure all data is written to the OutputStream
    objectOutputStream.flush();

    // Close the ObjectOutputStream and socket
    objectOutputStream.close();
    this.socket.close();
  }

  /**
   * Creates JSON response to send to Client.
   * @param message - Message to be sent to Client
   * @return response, a JSON string
   */
  private String createJSONRes(String message) throws Exception {
    JSONObject res = new JSONObject();
    res.put("status", "success");
    res.put("message", message);
    
    // get doc
    Document doc = this.databaseHandler.get("uid", String.valueOf(this.obj.get("uid")));
    if (doc == null) {
      throw new Exception("Could not locate document");
    }

    Integer rubies = doc.getInteger("rubies");
    Integer lives = doc.getInteger("lives");
    Integer spriteMap = doc.getInteger("spriteMap");

    if (rubies == null || lives == null || spriteMap == null) {
      throw new Exception("Missing required fields in document");
    }

    res.put("rubies", rubies);
    res.put("lives", lives);
    res.put("spriteMap", spriteMap);

    return res.toJSONString();
  }

  /**
   * Runs the Get request handler.
   */
  @Override
  public void run() {
    System.out.println("getRequestHandler.run() ran");
    try {
      sendResponse("Document located successfully");
    }  catch (Exception e) {
      System.err.println("Get request could not run");
    }
  }
}
