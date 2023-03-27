package org.sourceCode;

/**
 * Defines reachable checkpoints in game by the Player. Player to be
 * respawned at last reached checkpoint upon death.
 *
 * @author Greg Song
 * @version 2023-02-21
 */
public class Checkpoint extends Tile implements Collidable {
  public Checkpoint(int x, int y, Object sprite) {
    super(x, y, sprite);
  }

  public boolean collided() {
    return false;
  }

  public void save() {
    //SaveState.save();
  }
}
