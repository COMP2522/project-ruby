package org.project;

import java.awt.image.BufferedImage;

/**
 * Abstract class for a immovable Tile.
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class Tile {

  // below data will be fetched from the map data file and we don't need variables for each tile, these are static
  protected int x;
  protected int y;

//  public Object sprite;    // don't need this one
  public BufferedImage sprite;

  public boolean collision = false;

  public Tile() {
  }

  public Tile(int x, int y, Object sprite) {
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}