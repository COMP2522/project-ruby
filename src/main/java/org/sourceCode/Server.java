package org.sourceCode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class implements java Socket server
 * @author pankaj
 *
 */
public class Server {
  /* Constants */
  private static final int PORT = 5000;
  private static final int POOLSIZE = 10;
  private static final String POST = "POST";
  private static final String GET = "GET";

  // static ServerSocket variable
  private static ServerSocket server;
  private ExecutorService executor = null;
  private DatabaseHandler databasehandler;


  /**
   * Constructs a Server.
   */
  public Server() throws IOException {
    this.server = new ServerSocket(PORT);
    this.executor = Executors.newFixedThreadPool(POOLSIZE);
    this.databasehandler = new DatabaseHandler();
  }

  public String createJSON(String reqType, SaveState saveState) {
    JSONObject req = new JSONObject();

    req.put("reqType", reqType);

    if (reqType.equals(this.POST)) {
      Map data = new LinkedHashMap();
//      data.put("rubies", saveState.rubies);

//    data.put("lives", saveState.lives);

      JSONArray rowArray = new JSONArray();
      // TODO: parse tileMap
//    for (String row : saveState.spriteMap) {
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
   * Parses JSONString Request
   * @return JSONObject
   */
  public JSONObject parseJSON(String JSONstr) {
    JSONObject obj = (JSONObject) JSONValue.parse(JSONstr);
    return obj;
  }

  /**
   * Starts Server
   * @throws IOException
   * @throws InterruptedException
   * @throws ClassNotFoundException
   */
  public void start() throws IOException, InterruptedException, ClassNotFoundException {
    // to test random delays
    Random random = new Random();
    // keep listens indefinitely until receives 'exit' call or program terminates
    while(true){
      // take out later
      Thread.sleep(random.nextInt(5000));

      System.out.println("Waiting for the client request");

      // creating socket and waiting for client connection
      Socket socket = server.accept();

      // read from socket to ObjectInputStream object
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      String jsonString = null;
      try {
        jsonString = (String) ois.readObject();
      } catch (RuntimeException | ClassNotFoundException e) {
        System.err.println("Can't read message from Server.");
      }
      JSONObject obj = parseJSON(jsonString);

      // handle request
      if(obj.get("reqType").equals(this.POST)) {
        //thread
        System.out.println("RequestType:" + obj.get("reqType"));
        Runnable task = new PostRequestHandler(socket, obj);
        executor.submit(task);
      } else if (obj.get("reqType").equals(this.GET)){
        // handle
        System.out.println("RequestType:" + obj.get("reqType"));
        Runnable task = (Runnable) new GetRequestHandler(socket, obj);
        executor.submit(task);
      } else {
        System.err.println("ReqType Invalid" + obj.get("reqType"));
      }

      // create ObjectOutputStream object
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
      // send response

      //test
      SaveState savestate = new SaveState();
      String response = createJSON("POST",savestate);

      // write object to Socket
      oos.writeObject(response);

      // close resources
      ois.close();
      oos.close();
      socket.close();

      // terminate the server if client sends exit request
      String s1 = "no";
      if(s1.equalsIgnoreCase("exit")) break;
    }
    System.out.println("Shutting down Socket server!!");

    // close the ServerSocket object
    server.close();
  }

  public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
    Server server = new Server();
    server.start();
  }

}