package org.sourceCode;

import org.json.simple.JSONObject;

import java.net.Socket;

public class GetRequestHandler implements Runnable {
  private Socket socket;
  public GetRequestHandler(Socket socket, JSONObject obj) {
    this.socket = socket;
  }

  @Override
  public void run() {
    try  {
      System.out.println("get handler ran");
    } catch (Exception e) {
      System.err.println("get request cold not run");
    }
  }
}
