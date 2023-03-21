package org.example;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
  private static final String GET = "GET";
  private static final String POST = "POST";

  InetAddress host = null;


  /**
   * Constructs Client
   */
  public Client() {

  }

  public JSONObject createRequest(String reqType, ) {
    JSONObject req = new JSONObject();

    req.put("requestType", reqType);
    req.put("data", data);
  }
}
