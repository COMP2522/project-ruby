package org.sourceCode;

import org.json.simple.JSONObject;

import java.net.Socket;

public class PostRequestHandler implements Runnable {
  private Databasehandler DatabaseHandler;
  private Socket socket;

  public PostRequestHandler(Socket socket, Databasehandler databasehandler, JSONObject obj) {
    this.socket = socket;
    this.DatabaseHandler = databasehandler;
  }

  @Override
  public void run() {
    try {
      System.out.println("post handler ran");
    } catch (Exception e) {
      System.err.println("posthandler could not run");
    }
  }
}
