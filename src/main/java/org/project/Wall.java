package org.project;

/**
 * Defines the non-movable blocks/boundary for the game that can be assumed to be walls.
 *
 * @author Simrat Kaur, Greg Song
 * @version 2023-02-21
 */
public class Wall extends Tile implements Collidable {
  public Wall(int x, int y, Object sprite) {
    super(x, y, sprite);
  }

  public boolean collided() {
    return false;
  }
}
