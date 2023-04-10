package org.project.DataState;

import org.bson.Document;
import org.json.simple.*;

import java.io.*;
import java.net.Socket;

/**
 * Class that handles POST Requests received by Server.
 *
 * @version 2023-03-27
 */
public class PostRequestHandler implements Runnable {
  private final DatabaseHandler databaseHandler;
  private final Socket socket;
  private final JSONObject obj;

  /**
   * Constructs a new PostRequestHandler.
   *
   * @param socket, the client socket
   * @param obj,    a JSONObject containing request data
   */
  public PostRequestHandler(Socket socket, JSONObject obj) {
    this.socket = socket;
    this.databaseHandler = DatabaseHandler.getInstance();
    this.obj = obj;
  }

  /**
   * Sends response to Client.
   *
   * @param message a String
   * @throws IOException if error while sending response
   */
  public void sendResponse(String message) throws IOException {
    OutputStream outputStream = this.socket.getOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    String res = createJSONRes(message);
    objectOutputStream.writeObject(res); // Write data to the ObjectOutputStream
    objectOutputStream.flush(); // Flush the ObjectOutputStream to ensure all data is written to the OutputStream
    objectOutputStream.close(); // Close the ObjectOutputStream to release any resources it may be holding
    this.socket.close();
  }

  /**
   * Creates JSON response to send to Client.
   *
   * @param message a String, message to be sent to Client
   * @return response, a JSON string
   */
  private String createJSONRes(String message) {
    JSONObject res = new JSONObject();
    // put data
    res.put("status", "success");
    res.put("message", message);

    return res.toJSONString();
  }

  /**
   * Runs the POST request handler
   */
  @Override
  public void run() {
    try {
      System.out.println("post handler ran");
      // get existing Document to update
      String uid = (String) obj.get("uid");
      Document doc = this.databaseHandler.get("uid", uid);
      if (doc != null) {
        try {
          System.out.println("UID found");
          // update document with JSONObject values
          doc.put("rubies", obj.get("rubies"));
          doc.put("lives", obj.get("lives"));
          doc.put("spriteMap", obj.get("spriteMap"));
          this.databaseHandler.update("uid", uid, doc);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
        try {
          this.sendResponse("Data successfully updated");
        } catch (IOException e) {
//          throw new RuntimeException(e);
          System.err.println("Cant write to oos");
        }
      } else {
        // create new document
        System.out.println("UID not found");
        Document newDoc = new Document("uid", obj.get("uid"));
        newDoc.append("rubies", obj.get("rubies"));
        newDoc.append("lives", obj.get("lives"));
        newDoc.append("spriteMap", obj.get("spriteMap"));
        this.databaseHandler.put(newDoc);
      }
    } catch (Exception e) {
      System.err.println("posthandler could not run");
      e.printStackTrace();
    }
  }
}
