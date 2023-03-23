package org.sourceCode;


import java.awt.*;
import java.awt.Rectangle;

/**
 * Abstract class for Sprite.
 *
 * @author Amrit Singh
 * @version 2023-02-06
 */
abstract class Sprite implements Collidable {
  private int x;
  private int y;

  public Object sprite;
  public enum status {DISPLAYED, INVISIBLE, DELETED}
  
  public Sprite(int x, int y) { this.x = x; this.y = y; }

  public void setX(int x) { this.x = x; }
  public void setY(int y) { this.y = y; }

  public int getX() { return x; }
  public int getY() { return y; }
  
  @Override
  public boolean collided() { return false; }
}
