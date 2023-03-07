package org.example;

/**
 * Abstract class for a immovable Tile.
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
abstract class Tile {
  private int x;
  private int y;
  public Object sprite;

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
