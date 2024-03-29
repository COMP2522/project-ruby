package org.project.Datastate;

import java.io.*;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * Client handles interactions with Server, sends and receives data.
 *
 * @author Greg Song
 * @version 2023-03-27
 */
public class Client {
  /* Constants */
  private static final String GET = "GET";
  private static final String POST = "POST";

  /* Instance Variables */
  private String host;
  private final int port;


  /**
   * Constructs new Client.
   *
   * @param port an int,
   */
  public Client(int port) {
    try {
      host = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      System.err.println("Can't find host");
    }
    this.port = port;
  }

  /**
   * Sends JSONObject to Server.
   *
   * @param request a JSONObject.
   * @return response a JSONObject
   */
  public JSONObject sendRequest(String request) throws IOException {
    // setup
    Socket socket = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;

    // establish socket connection to server
    try {
      socket = new Socket(this.host, this.port);
    } catch (IOException e) {
      System.err.println("Can't connect to host socket.");
    }

    // open output stream
    try {
      assert socket != null;
      oos = new ObjectOutputStream(socket.getOutputStream());
    } catch (Exception e) {
      System.err.println("Can't connect to Out stream socket.");
    }
    System.out.println("Sending request to Socket Server");

    // write to socket output stream
    try {
      assert oos != null;
      oos.writeObject(request);
    } catch (Exception e) {
      System.err.println("Can't write object request: num");
    }

    // read the server response message
    try {
      ois = new ObjectInputStream(socket.getInputStream());
    } catch (Exception e) {
      System.err.println("Can't connect to input stream socket.");
    }

    // create jsonString from server response
    String jsonString = null;
    try {
      assert ois != null;
      jsonString = (String) ois.readObject();
    } catch (RuntimeException | ClassNotFoundException e) {
      System.err.println("Can't read message from Server.");
    }

    // Parse jsonString to JSONObject
    JSONObject jsonRes = null;
    try {
      JSONParser parser = new JSONParser();
      jsonRes = (JSONObject) parser.parse(jsonString);
    } catch (ParseException e) {
      System.err.println("Can't parse jsonString");
    }

    // close resources
    try {
      ois.close();
      oos.close();
    } catch (Exception e) {
      System.err.println("Can't close stream resources.");
    }
    return jsonRes;
  }

  /**
   * Creates a JSONString to be sent to server.
   *
   * @param reqType "POST" or "GET"
   * @return String JSONObject as String
   */
  public String createJSON(String reqType) {
    JSONObject req = new JSONObject();
    req.put("reqType", reqType);
    req.put("uid", SaveStateHandler.getInstance().getUsername());
    // for POST request
    if (reqType.equals(POST)) {
      System.out.println("creating request");
      req.put("playerData", SaveState.getInstance().playerData);
      req.put("gamePanelData", SaveState.getInstance().getGamePanelData());
    } else if (reqType.equals(GET)) {
      // create GET request JSONObject
      req.put("reqType", GET);
      req.put("uid", SaveStateHandler.getInstance().getUsername());
    } else {
      System.err.println("Invalid request type");
    }
    return req.toJSONString();
  }
}
