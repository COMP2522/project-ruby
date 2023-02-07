package org.example;

/**
 * Abstract class for Sprite.
 *
 * @author Amrit Singh
 * @version 2023-02-06
 */
abstract class Sprite {
  private int x;
  private int y;

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
