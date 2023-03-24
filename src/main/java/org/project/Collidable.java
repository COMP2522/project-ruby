package org.project;

/**
 * Interface implementing the collided method.
 *
 * @author Nathan Bartyuk
 * @version 2023-02-21
 */
public interface Collidable {

  /**
   * Returns true if one sprite shares a future position with another sprite.
   * Checks from left to right.
   *
   * @return true or false
   */
  boolean collided();
}
