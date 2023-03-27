package org.sourceCode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

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
  private int port;

  /**
   * Constructs new Client
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
   * Sends JSONObject to Server
   * @param request a JSONObject
   * @return response a JSONObject
   * @throws ParseException
   */
  public JSONObject sendRequest(String request) throws ParseException, IOException {
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
      oos = new ObjectOutputStream(socket.getOutputStream());
    } catch (Exception e) {
      System.err.println("Can't connect to Out stream socket.");
    }
    System.out.println("Sending request to Socket Server");

    // write to socket output stream
    try {
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
   * Creates a JSONString to be sent to server
   * @param reqType, "POST" or "GET"
   * @param saveState Object representing savestate of game
   * @return String, JSONObject as String
   */
  public String createJSON(String reqType, SaveState saveState) {
    // create JSONObject request to send to server
    JSONObject req = new JSONObject();
    // add kv pairs
    req.put("reqType", reqType);

    // for POST request
    if (reqType.equals(this.POST)) {
//      Map data = new LinkedHashMap();
//      data.put("rubies", saveState.rubies());
//    data.put("lives", player.lives());

      // rowArray for spriteMap (2d array)
      JSONArray rowArray = new JSONArray();

      //test
      // TODO: replace values with saveState.value
      System.out.println("creating request");
      req.put("uid", "1");
      req.put("rubies", Integer.valueOf(3));
      req.put("lives", Integer.valueOf(2));
      // add spriteMap rows to rowArray
//      for (String row : saveState.spriteMap) {
//        rowArray.add(row);
//      }
      rowArray.add("1111");
      rowArray.add("2222");
      rowArray.add("3333");

      req.put("spriteMap", rowArray);

    } else if (reqType.equals(this.GET)) {
      // create GET request JSONObject
      req.put("reqType", this.GET);
      req.put("uid", "1");
    } else {
      System.err.println("Invalid request type");
    }

    // return JSONObject as String
    return req.toJSONString();
  }

  /**
   * TODO: maybe move somewhere else
   */
  public void loadGame() {
    // send request - returns JSONObject
    // take JSONObject and parse data
    // save to savestate
    // init window with savestate data
  }

  /**
   *
   * @param args
   * - took out throws IOException, ClassNotFoundException, InterruptedException
   * - after try catch blocks, still getting nullpointerexception?
   * - to show why we want good exceptions
   */
  public static void main(String[] args) throws ParseException, IOException {
    Client client = new Client(5000);

    SaveState saveState = new SaveState();
    String request = client.createJSON("GET", saveState);

    // testing multiple requests
    while (true) {
      JSONObject jsonRes = client.sendRequest(request);
      // test print TODO:-- currently return null values as databasehandler.get is not async
      System.out.println("in loop RESPONSE: " + jsonRes.toJSONString());

    }
  }
}
