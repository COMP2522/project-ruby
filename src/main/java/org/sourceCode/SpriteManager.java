package org.sourceCode;

/**
 * Manages the status and position of each sprite.
 *
 * @author Nathan Bartyuk
 * @version 2023-02-21
 */
public class SpriteManager {
  public Map map;
  public Sprite s1;
  public Sprite s2;
  
  public boolean checkCollided() {
    if (s1.collided() && s2.collided()){
      return true;
    } else {
      return false;
    }
  }
  
  public void updateMap() {}
  
  public void moveSprite() {}
  
  public void removeSprite() {}
}
