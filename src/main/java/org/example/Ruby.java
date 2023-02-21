package org.example;

/**
 * Defines the rubies that the player collects.
 *
 * @author Simrat Kaur
 * @version 2023-02-07
 */
public class Ruby extends Sprite implements Collidable, Gravity {
  
  public status rubyStatus;
  
  /**
   * Constructs Ruby object.
   * @param x x-position of this object, an int
   * @param y y-position of this object, an int
   */
  public Ruby(int x, int y) {
    super(x, y);
    rubyStatus = status.DISPLAYED;
  }
  
  public boolean collide() {
    return false;
  }
  
  public boolean fall() {
    return false;
  }
}
