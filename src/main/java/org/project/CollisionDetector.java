package org.project;

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
  public static GamePanel gp;
  public static CollisionDetector instance;
  
  private int leftCol;
  private int rightCol;
  private int upRow;
  private int downRow;

  /**
   * Constructs a CollisionDetector object with a reference to
   * the GamePanel it is being used in.
   * @param gp The GamePanel object.
   */
  private CollisionDetector(GamePanel gp) {
    CollisionDetector.gp = gp;
  }
  
  public static CollisionDetector getCollisionDetector(GamePanel gp) {
    if (instance == null) {
      instance = new CollisionDetector(gp);
    }
    return instance;
  }

  /**
   * Checks for collision between the player and tiles in the game world.
   * @param entity The Entity object representing the player
   */
  public void checkTile(Entity entity) {
    int leftX = entity.worldX + entity.hitbox.x;
    int rightX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
    int upY = entity.worldY + entity.hitbox.y;
    int downY = entity.worldY + entity.hitbox.y + entity.hitbox.height;

    leftCol = leftX / TILE_SIZE;
    rightCol = rightX / TILE_SIZE;
    upRow = upY / TILE_SIZE;
    downRow = downY / TILE_SIZE;


    //checking the player's direction
    if (entity.direction == directions.UP) {
      upRow = (upY - entity.speed) / TILE_SIZE;
      collide(entity);
    } else if (entity.direction == directions.DOWN) {
      downRow = (downY + entity.speed) / TILE_SIZE;
      collide(entity);
    } else if (entity.direction == directions.LEFT) {
      leftCol = (leftX - entity.speed) / TILE_SIZE;
      collide(entity);
    } else if (entity.direction == directions.RIGHT) {
      rightCol = (rightX + entity.speed) / TILE_SIZE;
      collide(entity);
    }
  }

  private void collide(Entity entity) {
    int tileNum1, tileNum2;

    tileNum1 = gp.tManager.map[leftCol][upRow];
    tileNum2 = gp.tManager.map[rightCol][downRow];

    if (gp.tManager.tiles[tileNum1].collision || gp.tManager.tiles[tileNum2].collision) {
      entity.collision = true;
    }
  }

  /**
   * Checks for collision between the player and intractable elements in the game world,
   * such as rubies, doors, and power-ups.
   * @param p      The Player object
   * @return The index of the element with which the player is colliding, or 999 if no collision occurs.
   */
  public int checkObject(Player p, boolean player) {
    int index = MAX_INDEX;
    for (int i = 0; i < gp.elements.length; i++) {
      if (gp.elements[i] != null) {

        //get entity/player's solid area position
        p.hitbox.x = p.worldX + p.hitbox.x;
        p.hitbox.y = p.worldY + p.hitbox.y;

        //get the object's solid area position
        gp.elements[i].getHitbox().x = gp.elements[i].getWorldX() + gp.elements[i].getHitbox().x;
        gp.elements[i].getHitbox().y = gp.elements[i].getWorldY() + gp.elements[i].getHitbox().y;

        if (p.direction == directions.UP) {
          p.hitbox.y -= p.speed;
          index = handleCollision(p,gp, player, i, index);
        } else if (p.direction == directions.DOWN) {
          p.hitbox.y += p.speed;
          index = handleCollision(p, gp,player, i, index);
        } else if (p.direction == directions.LEFT) {
          p.hitbox.x -= p.speed;
          index = handleCollision(p,gp, player, i, index);
        } else if (p.direction == directions.RIGHT) {
          p.hitbox.x += p.speed;
          index = handleCollision(p,gp, player, i, index);
        }

        //reset hitboxes to default values.
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
   * @param p         The Player object
   * @param gp1       The gamePanel object
   * @param player    Boolean indicating if player collision should be tracked
   * @param index     Index of game object with which player is colliding
   */
  private int handleCollision(Player p, GamePanel gp1,boolean player, int i, int index) {
    if (p.hitbox.intersects(gp1.elements[i].getHitbox())) {
      if (gp1.elements[i].getCollision()) {
        p.collision = true;
      }
      if (player) {
        index = i;
      }
    }
    return index;
  }


  // NPC and for Future use will include Monster
  public int checkEntityCollide(Entity entity, Entity[] target) {
    int index = MAX_INDEX;
    for (int i = 0; i < target.length; i++) {
      if (target[i] != null) {

        //get entity/player's solid area position
        entity.hitbox.x = entity.worldX + entity.hitbox.x;
        entity.hitbox.y = entity.worldY + entity.hitbox.y;

        //get teh object's solid area position
        target[i].hitbox.x = target[i].worldX + target[i].hitbox.x;
        target[i].hitbox.y = target[i].worldY + target[i].hitbox.y;

        int moveX = 0;
        int moveY = 0;
  
        switch (entity.direction) {
          case UP -> moveY = -entity.speed;
          case DOWN -> moveY = entity.speed;
          case LEFT -> moveX = -entity.speed;
          case RIGHT -> moveX = entity.speed;
        }

        entity.hitbox.x += moveX;
        entity.hitbox.y += moveY;

        if (entity.hitbox.intersects(target[i].hitbox)) {
          entity.collision = true;
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

  public boolean checkPlayerCollide(Entity entity) {
    boolean contactPlayer = false;

    //get entity/player's solid area position
    entity.hitbox.x = entity.worldX + entity.hitbox.x;
    entity.hitbox.y = entity.worldY + entity.hitbox.y;

    //get the object's solid area position
    gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
    gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;
    
  
    switch (entity.direction) {
      case UP -> entity.hitbox.y -= entity.speed;
      case DOWN -> entity.hitbox.y += entity.speed;
      case LEFT -> entity.hitbox.x -= entity.speed;
      case RIGHT -> entity.hitbox.x += entity.speed;
    }

    if (entity.hitbox.intersects(gp.player.hitbox)) {
      entity.collision = true;
      contactPlayer = true;
    }

    entity.hitbox.x = entity.hitboxDefaultX;
    entity.hitbox.y = entity.hitboxDefaultY;

    gp.player.hitbox.x = gp.player.hitboxDefaultX;
    gp.player.hitbox.y = gp.player.hitboxDefaultY;

    return contactPlayer;
  }

}
