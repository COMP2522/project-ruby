package org.sourceCode;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.sourceCode.EventListener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Defines the server, which manages game state.
 *
 * @author Nathan Bartyuk, Greg Song
 * @version 2023-03-17
 */
public class Server {
  /* Static variables */
  private static int PORT = 5000;
  private List<Socket> clientSockets = new ArrayList<Socket>();
  private List<EventListener> eventListeners = new ArrayList<EventListener>();

  // thread pool
  /**
   * paul note: can let threads run if modular enough
   * - otherwise need to manage threads
   */


  /* Game specific */
  private SaveState currentSave;

  /**
   * Constructs Server.
   */
  public Server() throws IOException {
    // init EventListeners

  }

  /* Methods */

  /**
   * Runs Server
   * @throws IOException
   */
  public void start() throws IOException {
    // Initialization
    ServerSocket server = null;
    try {
      // Starts server
      server = new ServerSocket(PORT);
      // print
      System.out.println("Server started");
      System.out.println("Waiting for Client..");
    } catch (IOException e){
      throw new IOException(e);
    }

    // Event Listeners
    // TODO: make EventListeners for requests (saveGame - POST, loadGame - GET)
    PostEventListener postListener = new PostEventListener();
    GetEventListener getListener = new GetEventListener();

    // test
    System.out.println("Server started on " + server.getInetAddress().getHostAddress() + ":" + server.getLocalPort());

    // runs until termination
    while(true) {
      // read object from socket - need to read requestType
      try {
        System.out.println("Waiting for the client request");

        // creating socket and waiting for client connection
        Socket socket = server.accept();

        // read from socket to ObjectInputStream object
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        // sn: above,
        // convert ObjectInputStream object to String
        String message = (String) ois.readObject();
        System.out.println("Message Received: " + message);

        // create ObjectOutputStream object
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        // write object to Socket
        oos.writeObject("Hi Client "+message);

        // close resources
        ois.close();
        oos.close();
        socket.close();

        // terminate the server if client sends exit request
        if(message.equalsIgnoreCase("exit")) break;

        // initialize socket waiting for client connection
//        Socket socket = server.accept();
//        socket.setSoTimeout(5000);
//
//      // test print
//        System.out.println("in while loop");

        // read object from socket
//        InputStreamReader in = new InputStreamReader(socket.getInputStream());
//        BufferedReader reader = new BufferedReader(in);
//        int c;
//        StringBuilder reqBuilder = new StringBuilder();
//        while((c = reader.read()) != -1) {
//          reqBuilder.append((char) c);
//        }
//        String json = reqBuilder.toString();
//        JSONObject req = (JSONObject) JSONValue.parse(json);


        //test print json
//        System.out.println("json obj: " + req);
//
//        String reqType = (String) req.get("requestType");
//
//        // handleRequest
//        if (reqType.equals("POST")) {
//          new Thread(() -> postListener.onTrigger()).start();
//          // onTrigger creates response and then send from here?
//        } else if (reqType.equals("GET")) {
//          new Thread(() -> getListener.onTrigger()).start();
//          // send response
//        }



        // test print
        System.out.println("Message Received:");

      } catch (IOException e) {
        System.err.println("Error: Client Connection");
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e); // for readobject
      }


      // save to file in some directory?
      // return status?
      // also need one for returning data and client would need to parse data

      // close resources
//      socket.close();

      // close ServerSocket
      server.close();
    }
  }

  public void newGame() {
//    Map map = new Map();
    Player player = new Player();
  }

  public void loadGame() {
    Map map = currentSave.translateMap();
    Player player = new Player(currentSave.x(), currentSave.y(), currentSave.rubies());
  }

  public static void main (String args[]) throws IOException {
    Server server = new Server();
    server.start();
  }


}
