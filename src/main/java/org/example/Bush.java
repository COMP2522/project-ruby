package org.example;

/**
 * Defines Bush.
 *
 * @author Greg Song
 * @version 2023-02-09
 */
public class Bush extends Sprite implements Collidable {

  public status bushStatus;

  /**
   * Constructs Bush object.
   * @param x x-position of this object, an int
   * @param y y-position of this object, an int
   */
  public Bush(int x, int y) {
    super(x, y);
    bushStatus = status.DISPLAYED;
  }
  
  
  public boolean collide() {
    return false;
  }
}
