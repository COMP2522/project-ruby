package org.sourceCode;

/**
 * Defines the server, which manages game state.
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class Server {
  private SaveState currentSave;

  public void newGame() {
////    Map map = new Map();
//    Player player = new Player();
  }

  public void loadGame() {
    Map map = currentSave.translateMap();
//    Player player = new Player(currentSave.x(), currentSave.y(), currentSave.rubies());
  }
}
