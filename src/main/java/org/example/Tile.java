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

  public Tile(int x, int y, Object sprite) {
    this.x = x;
    this.y = y;
    this.sprite = sprite;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
