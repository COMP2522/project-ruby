package org.sourceCode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;


public class Client {
  /* Constants */
  private static final String GET = "GET";
  private static final String POST = "POST";

  /* Instance variables */
  InetAddress host = null;
  private int port;

  /**
   * Constructs Client
   */
  public Client(int port) {
    try {
      host = InetAddress.getLocalHost();
    } catch (UnknownHostException e) {
      System.err.println("Can't find host");
    }
    this.port = port;
  }

  /**
   * Sends request to Server
   * @param reqType
   * @param saveState
   * @return
   */
  public String sendRequest(String reqType, SaveState saveState) {


    Socket socket = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    // sn: up to here is setup, everything under runs until process ended
    int i = 0;
    while (true) {

      // establish socket connection to server
      try {
        socket = new Socket(host.getHostName(), 5000);
      } catch (Exception e) {
        System.err.println("Can't connect to host socket.");
//        throw new RuntimeException(e);
      }

      // write to socket using ObjectOutputStream
      try {
        oos = new ObjectOutputStream(socket.getOutputStream());
      } catch (Exception e) {
//        throw new RuntimeException(e);
        System.err.println("Can't connect to Out stream socket.");
      }
      System.out.println("Sending request to Socket Server");

      if (i == 1000) { //sn: upto 1000 messages
        try {
          oos.writeObject("exit");
        } catch (Exception e) {
//          throw new RuntimeException(e);
          System.err.println("Can't write object 'exit'");
        }
        break;
      }

      try {
        oos.writeObject("Request: " + i);
      } catch (Exception e) {
//        throw new RuntimeException(e);
        System.err.println("Can't write object request: num");
      }
      i++;

      // read the server response message
      try {
        ois = new ObjectInputStream(socket.getInputStream());
      } catch (Exception e) {
//        throw new RuntimeException(e);
        System.err.println("Can't connect to input stream socket.");
      }
      String message = null;
      try {
        message = (String) ois.readObject();
      } catch (Exception e) {
//        throw new RuntimeException(e);
        System.err.println("Can't read message from Server.");
      }
      System.out.println("Message: " + message);

      // close resources
      try {
        ois.close();
        oos.close();
      } catch (Exception e) {
//        throw new RuntimeException(e);
        System.err.println("Can't close stream resources.");
      }
      //
//    Socket socket = null;
//    PrintWriter out = null;
//    BufferedReader in = null;
//
//    //
//    String json = createJSON(reqType, saveState);
//
//    try {
//      socket = new Socket(host.getHostName(), this.port);
//    } catch (IOException e) {
//      System.err.println("Can't connect to host socket");
//    }
//
//
    }
    return null;
  }

  public String createJSON(String reqType, SaveState saveState) {
    JSONObject req = new JSONObject();

    req.put("requestType", reqType);

    if (reqType.equals(this.POST)) {
      Map data = new LinkedHashMap();
//      data.put("rubies", saveState.rubies());

//    data.put("lives", player.lives());

      JSONArray rowArray = new JSONArray();
      // TODO: parse tileMap
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
    System.out.println(req);
    return req.toJSONString();
  }

  public static void main(String[] args) {
    Client client = new Client(5000);
//    String temp = client.createJSON("POST", null);
    System.out.println("Client: request created");
    System.out.println("Client: sending request");
    String res = client.sendRequest("POST", null);
    System.out.println(res);
//    client.sendRequest("POST", null);
  }

}
