package org.project;

import org.json.simple.*;
import java.io.*;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

/**
 * Server that handles requests and interactions with MongoDB.
 *
 * @author Greg Song
 * @version 2023-03-27
 */
public class Server {
  /* Constants */
  private static final int PORT = 5000;
  private static final int POOLSIZE = 10;
  private static final String POST = "POST";
  private static final String GET = "GET";

  private final ServerSocket server;
  private final ExecutorService executor;
  private final DatabaseHandler databasehandler;
  
  
  /**
   * Constructs a Server.
   */
  public Server() throws IOException {
    this.server = new ServerSocket(PORT);
    this.executor = Executors.newFixedThreadPool(POOLSIZE);
    this.databasehandler = DatabaseHandler.getInstance();
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
   */
  public void start() throws IOException, InterruptedException {
    // to test random delays
    Random random = new Random();
    // runs indefinitely
    while(true){
      // take out later -
      Thread.sleep(random.nextInt(5000));
      
      System.out.println("Waiting for the client requests..");
      
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
      if(obj.get("reqType").equals(POST)) {
        //thread
        System.out.println("RequestType:" + obj.get("reqType"));
        Runnable task = new PostRequestHandler(socket, obj);
        executor.submit(task);
      } else if (obj.get("reqType").equals(GET)){
        // handle
        System.out.println("RequestType:" + obj.get("reqType"));
        Runnable task = (Runnable) new GetRequestHandler(socket, obj);
        executor.submit(task);
      } else {
        System.err.println("ReqType Invalid" + obj.get("reqType"));
      }

//      test
      SaveState savestate = SaveState.getInstance();
      
      // close resources TODO: close ois this without crashing
      // crashing when closing ois here with postreqhandler
//      ois.close();
      // handled in Handler
//      oos.close();
//      socket.close();
      
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