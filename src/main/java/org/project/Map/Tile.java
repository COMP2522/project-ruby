package org.project.Map;

import java.awt.image.BufferedImage;

/**
 * Abstract class for a static element on the map
 * @author Nathan Bartyuk
 * @version 2023-03-28
 */
public class Tile {
  public BufferedImage sprite;
  public boolean collision = false;
}
