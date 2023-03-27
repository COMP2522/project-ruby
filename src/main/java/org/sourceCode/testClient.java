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
 * Client
 * @author
 *
 */
public class testClient {
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
  public testClient(int port) {
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

    // write to socket using ObjectOutputStream
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
    } catch (Exception e) {
//        throw new RuntimeException(e);
      System.err.println("Can't connect to Out stream socket.");
    }
    System.out.println("Sending request to Socket Server");

    //
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
    String jsonString = null;
    try {
      jsonString = (String) ois.readObject();
    } catch (RuntimeException | ClassNotFoundException e) {
      System.err.println("Can't read message from Server.");
    }
    // Parse JSON
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
    JSONObject req = new JSONObject();

    req.put("reqType", reqType);

    if (reqType.equals(this.POST)) {
      Map data = new LinkedHashMap();
//      data.put("rubies", saveState.rubies());

//    data.put("lives", player.lives());

      JSONArray rowArray = new JSONArray();
      // TODO: parse tileMap and add by row
//    for (String row : spriteMap) {
//      rowArray.add(row);
//    }
      // data.put("tileMap", rowArray);


      //test
      System.out.println("creating request");
      data.put("rubies", Integer.valueOf(3));
      data.put("lives", Integer.valueOf(2));
      rowArray.add("1111");
      rowArray.add("2222");
      rowArray.add("3333");
//      String[] tempMap = {"11111", "2222", "33333"};
      data.put("tileMap", rowArray);
      req.put("data", data);

    } else if (reqType.equals(this.GET)) {
      req.put("data", null);
    } else {
      System.err.println("Invalid request type");
    }

    // test print
//    System.out.println(req);
    return req.toJSONString();
  }


  /**
   *
   * @param args
   * - took out throws IOException, ClassNotFoundException, InterruptedException
   * - after try catch blocks, still getting nullpointerexception?
   * - to show why we want good exceptions
   */
  public static void main(String[] args) throws ParseException, IOException {
    testClient client = new testClient(5000);

    SaveState saveState = new SaveState();
    String request = client.createJSON("POST", saveState);

    // testing multiple requests
    while (true) {
      JSONObject jsonRes = client.sendRequest(request);
      // test print
      System.out.println("in loop RESPONSE: " + jsonRes.toJSONString());

    }
  }
}