package org.example;

/**
 * Defines the Boulder class and the methods that will be implemented for its function.
 *
 * @author Amrit Singh
 * @version 2023-02-06
 */
public class Boulder extends Sprite implements Gravity {
  public status boulderStatus;
  
  /**
   * Constructs Boulder object.
   * @param x x-position of this object, an int
   * @param y y-position of this object, an int
   */
  public Boulder(int x, int y) {
    super(x, y);
    boulderStatus = status.DISPLAYED;
  }
  
  @Override
  public boolean collided() {
    return false;
  }
  
  @Override
  public boolean fall() {
    return false;
  }
}
