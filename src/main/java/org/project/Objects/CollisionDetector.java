package org.project.Objects;

import org.project.Entities.Entity;
import org.project.Entities.Player;
import org.project.UI.GamePanel;

import static org.project.SystemVariables.*;

/**
 * The CollisionDetector class provides methods for detecting
 * collisions between the player, tiles, and intractable elements
 * in the game.
 *
 * @author Nathan Bartyuk, Simrat Kaur, Abhishek Chouhan, Amrit Jhatu, Greg
 * @version 2023-02-07
 */
public class CollisionDetector {

  // instance of gamePanel in which collision is happening.
  private static GamePanel gp;

  // a singleton instance of collision detector because each game needs only one
  private static CollisionDetector instance;

  // stores the int-code data of adjacent tiles in above, below, left and right
  // for checking if there is a solid tile adjacent current tile being checked
  private int leftCol;
  private int rightCol;
  private int upRow;
  private int downRow;

  /**
   * Constructs a CollisionDetector object with a reference to
   * the GamePanel it is being used in.
   *
   * @param gp The GamePanel object.
   */
  private CollisionDetector(GamePanel gp) {
    CollisionDetector.gp = gp;
  }

  /**
   * method that returns a single instance of Collision Detector class.
   * Returns a new instance if called first time else returns previous instance.
   *
   * @param gp the gamePanel in which collision is happening.
   * @return CollisionDetector instance
   */
  public static CollisionDetector getCollisionDetector(GamePanel gp) {
    if (instance == null) {
      instance = new CollisionDetector(gp);
    }
    return instance;
  }

  /**
   * Checks for collision between the player and tiles in the game world.
   *
   * @param entity The Entity object representing the player
   */
  public void checkTile(Entity entity) {
    int leftX = entity.getWorldX() + entity.hitbox.x;
    int rightX = leftX + entity.hitbox.width;
    int upY = entity.getWorldY() + entity.hitbox.y;
    int downY = upY + entity.hitbox.height;

    /* stores the int code of tiles adjacent to the tile the entity is on currently */
    leftCol = leftX / TILE_SIZE;
    rightCol = rightX / TILE_SIZE;
    upRow = upY / TILE_SIZE;
    downRow = downY / TILE_SIZE;


    /* checks performed below, if entity is deemed to running into a solid tile,
     * entity collision boolean is set to be true
     * movement is stopped in the entity's update method which has an if statement to only update
     * entity position if collision is false.
     */
    if (entity.peekDirection() == directions.UP) {
      upRow = (upY - entity.getSpeed()) / TILE_SIZE;
      collide(entity); // sets entity collision to true
    } else if (entity.peekDirection() == directions.DOWN) {
      downRow = (downY + entity.getSpeed()) / TILE_SIZE;
      collide(entity);
    } else if (entity.peekDirection() == directions.LEFT) {
      leftCol = (leftX - entity.getSpeed()) / TILE_SIZE;
      collide(entity);
    } else if (entity.peekDirection() == directions.RIGHT) {
      rightCol = (rightX + entity.getSpeed()) / TILE_SIZE;
      collide(entity);
    }
  }

  /**
   * method that checks if the tile Entity will next step on has collision property
   * and sets entity collision to true, meaning entity is colliding.
   * This prevents the entity's update position method from running, hence entity is stopped.
   *
   * @param entity The entity on which check is being performed.
   */
  private void collide(Entity entity) {
    int tileNum1, tileNum2;

    tileNum1 = gp.tManager.getMap()[leftCol][upRow];
    tileNum2 = gp.tManager.getMap()[rightCol][downRow];

    // check if the tiles the entity is colliding with have the collision property
    // the tiles have a collision boolean that is set to true for solid tiles and false for tiles
    // that can be walked on
    if (gp.tManager.tiles[tileNum1].collision || gp.tManager.tiles[tileNum2].collision) {
      entity.setCollided(true); // set entity collision to true
    }
  }

  /**
   * Checks for collision between the player and intractable elements in the game world,
   * such as rubies, doors, and power-ups.
   *
   * @param p The Player object
   * @return The index of the element which the player is colliding, or 999 if no collision occurs.
   */
  public int checkObject(Player p, boolean player) {
    int index = MAX_INDEX;
    for (int i = 0; i < gp.elements.length; i++) {
      if (gp.elements[i] != null) {

        // get entity/player's solid area position in the world map
        p.hitbox.x = p.getWorldX() + p.hitbox.x;
        p.hitbox.y = p.getWorldY() + p.hitbox.y;

        // get the object's solid area position in the world map, because the hitbox
        // value is relative to object
        // and we need its position on the world map to perform checks
        // just add worldX to relative position of solid area to get the values.
        gp.elements[i].getHitbox().x = gp.elements[i].getWorldX() + gp.elements[i].getHitbox().x;
        gp.elements[i].getHitbox().y = gp.elements[i].getWorldY() + gp.elements[i].getHitbox().y;

        // check if player is in range of object to pick it up
        if (p.peekDirection() == directions.UP) {
          p.hitbox.y -= p.getSpeed();
          index = handleCollision(p, gp, player, i, index);
        } else if (p.peekDirection() == directions.DOWN) {
          p.hitbox.y += p.getSpeed();
          index = handleCollision(p, gp, player, i, index);
        } else if (p.peekDirection() == directions.LEFT) {
          p.hitbox.x -= p.getSpeed();
          index = handleCollision(p, gp, player, i, index);
        } else if (p.peekDirection() == directions.RIGHT) {
          p.hitbox.x += p.getSpeed();
          index = handleCollision(p, gp, player, i, index);
        }

        // Reset hitbox's to default values because we momentarily
        // set them to be relative to worldMap
        p.hitbox.x = p.hitboxDefaultX;
        p.hitbox.y = p.hitboxDefaultY;
        gp.elements[i].getHitbox().x = 0;
        gp.elements[i].getHitbox().y = 0;
      }
    }
    return index;
  }

  /**
   * Helper function to handle collision between player and game object.
   *
   * @param p         The Player object
   * @param gp1       The gamePanel object
   * @param player    Boolean indicating if player collision should be tracked
   * @param index     Index of game object with which player is colliding
   */
  private int handleCollision(Player p, GamePanel gp1, boolean player, int i, int index) {
    if (p.hitbox.intersects(gp1.elements[i].getHitbox())) {
      if (gp1.elements[i].getCollision()) {
        p.setCollided(true);
      }
      if (player) {
        index = i;
      }
    }
    return index;
  }

  /**
   * Check collision of entity with entity.
   *
   * @param entity the entity on which the collision is being checked on
   * @param target the entities against which collision is being checked
   * @return the index of the target entity which collide with the entity which the check was done on.
   */
  public int checkEntityCollide(Entity entity, Entity[] target) {
    int index = MAX_INDEX;
    for (int i = 0; i < target.length; i++) {
      if (target[i] != null) {

        //get entity/player's solid area position
        entity.hitbox.x = entity.getWorldX() + entity.hitbox.x;
        entity.hitbox.y = entity.getWorldY() + entity.hitbox.y;

        //get teh object's solid area position
        target[i].hitbox.x = target[i].getWorldX() + target[i].hitbox.x;
        target[i].hitbox.y = target[i].getWorldY() + target[i].hitbox.y;

        int moveX = 0;
        int moveY = 0;
  
        switch (entity.peekDirection()) {
          case UP -> moveY = -entity.getSpeed();
          case DOWN -> moveY = entity.getSpeed();
          case LEFT -> moveX = -entity.getSpeed();
          case RIGHT -> moveX = entity.getSpeed();
        }

        entity.hitbox.x += moveX;
        entity.hitbox.y += moveY;

        if (entity.hitbox.intersects(target[i].hitbox)) {
          entity.setCollided(true);
          index = i;
        }

        entity.hitbox.x = entity.hitboxDefaultX;
        entity.hitbox.y = entity.hitboxDefaultY;
        target[i].hitbox.x = target[i].hitboxDefaultX;
        target[i].hitbox.y = target[i].hitboxDefaultY;
      }
    }
    return index;
  }

  /**
   * method to check if player is colliding with Entity.
   *
   * @param entity the Entities (all of them)
   * @return contactPlayer a boolean to indicate that player had contact with Entity
   */
  public boolean checkPlayerCollide(Entity entity) {
    boolean contactPlayer = false;

    //get entity/player's solid area position
    entity.hitbox.x = entity.getWorldX() + entity.hitbox.x;
    entity.hitbox.y = entity.getWorldY() + entity.hitbox.y;

    //get the object's solid area position
    gp.player.hitbox.x = gp.player.getWorldX() + gp.player.hitbox.x;
    gp.player.hitbox.y = gp.player.getWorldY() + gp.player.hitbox.y;
    
  
    switch (entity.peekDirection()) {
      case UP -> entity.hitbox.y -= entity.getSpeed();
      case DOWN -> entity.hitbox.y += entity.getSpeed();
      case LEFT -> entity.hitbox.x -= entity.getSpeed();
      case RIGHT -> entity.hitbox.x += entity.getSpeed();
    }

    if (entity.hitbox.intersects(gp.player.hitbox)) {
      entity.setCollided(true);
      contactPlayer = true;
    }

    entity.hitbox.x = entity.hitboxDefaultX;
    entity.hitbox.y = entity.hitboxDefaultY;

    gp.player.hitbox.x = gp.player.hitboxDefaultX;
    gp.player.hitbox.y = gp.player.hitboxDefaultY;

    return contactPlayer;
  }

}
