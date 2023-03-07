package org.example;

/**
 * Interface implementing the fall method.
 *
 * @author Nathan Bartyuk
 * @version 2023-02-21
 */
public interface Gravity {

  /**
   * Returns true if one side of the sprite has a pit (2 sprites down) right next to it.
   * Checks from right to left.
   *
   * @return true or false
   */
  boolean fall();
}
