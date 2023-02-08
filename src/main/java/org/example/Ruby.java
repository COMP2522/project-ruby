package org.example;

/**
 * Defines the rubies that the player collects.
 *
 * @author Simrat Kaur
 * @version 2023-02-07
 */
public class Ruby extends Sprite {

  // Indicates whether the ruby is displayed or not.
  private boolean displayed;

  /**
   * Checks if the diamonds have been collected or not.
   *
   * @return 'false' if the diamond has not been collected,'true' otherwise.
   */
  public boolean checkCollected(){
    return false;
  }

  // Makes the diamonds fall.
  public void fall(){}
}
