package org.example;

/**
 * Defines Bush.
 *
 * @author Greg Song
 * @version 2023-02-09
 */
public class Bush extends Sprite implements Collidable {
  private Image sprite;
  private Status status;

  /**
   * Constructs Bush object.
   * @param x x-position of this object, an int
   * @param y y-position of this object, an int
   */
  public Bush(int x, int y, status) {
    super(x, y);
    this.status = status;
  }

  /**
   * Checks if Bush has collided.
   *
   * @return true if collided
   */
  public boolean collide() {
    return false;
  }
}
