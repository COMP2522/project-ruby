package org.example;

/**
 * Defines a save object which stores the current game state in a file
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class SaveState {
  public enum tile {BG, WA, CH}
  public enum sprite {BU, BO, RU}

  public tile[][] tileMap;
  public sprite[][] spriteMap;
  private int playerX;
  private int playerY;
  private int playerRubies;

  public int x() {
    return playerX;
  }

  public int y() {
    return playerY;
  }

  public int rubies() {
    return playerRubies;
  }

  public Map translateMap() {
    Map map = new Map(this);
    return null;
  }

  public void save() {}

  public void load() {}
}
