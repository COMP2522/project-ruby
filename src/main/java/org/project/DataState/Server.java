package org.project.DataState;

import org.json.simple.*;

import java.io.*;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
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
  private static final int POOL_SIZE = 10;
  private static final String POST = "POST";
  private static final String GET = "GET";

  private final ServerSocket server;
  private final ExecutorService executor;
  
  
  /** Constructs a Server. */
  public Server() throws IOException {
    this.server = new ServerSocket(PORT);
    this.executor = Executors.newFixedThreadPool(POOL_SIZE);
  }
  
  /**
   * Parses JSONString Request
   * @return JSONObject
   */
  public JSONObject parseJSON(String JSONstr) {
    return (JSONObject) JSONValue.parse(JSONstr);
  }
  
  /**
   * Starts Server
   */
  public void start() throws IOException {
    while(true){
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
        System.out.println("RequestType:" + obj.get("reqType")); //thread
        Runnable task = new PostRequestHandler(socket, obj);
        executor.submit(task);
      } else if (obj.get("reqType").equals(GET)){
        System.out.println("RequestType:" + obj.get("reqType")); // handle
        Runnable task = new GetRequestHandler(socket, obj);
        executor.submit(task);
      } else {
        System.err.println("ReqType Invalid" + obj.get("reqType"));
      }
    }
  }
}