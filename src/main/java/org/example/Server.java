package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
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


  /* Game specific */
  private SaveState currentSave;

  /**
   * Constructs Server.
   */
  public Server() throws IOException {


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


    // runs until termination
    while(true) {
      // read object from socket - need to read requestType
      try {
        // initialize socket waiting for client connection
        Socket socket = server.accept();

        // read object from socket - need to read requestType
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        // parse JSON

        // get requestType
        String message = (String) in.readObject();



        // test print
        System.out.println("Message Recieved:");
        System.out.println(message);
      } catch (IOException e) {
        System.err.println("Error: Client Connection");
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }



      // save to file in some directory?
      // return status?
      // also need one for returning data and client would need to parse data

      // close resources
      socket.close();

      // close ServerSocket
      server.close();
    }
  }

  public void newGame() {
    Map map = new Map();
    Player player = new Player();
  }

  public void loadGame() {
    Map map = currentSave.translateMap();
    Player player = new Player(currentSave.x(), currentSave.y(), currentSave.rubies());
  }

  public static void main (String args[]) throws IOException {
    Server server = new Server();
  }


}
