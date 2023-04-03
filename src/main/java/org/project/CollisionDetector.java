package org.project;

import static org.project.Entity.directions.*;

/**
 * The CollisionDetector class provides methods for detecting
 * collisions between the player, tiles, and intractable elements
 * in the game.
 *
 * @author Nathan Bartyuk, Simrat Kaur, Abhishek Chouhan, Amrit Jhatu, Greg
 * @version 2023-02-07
 */
public class CollisionDetector {
  GamePanel gp;
  int leftCol;
  int rightCol;
  int upRow;
  int downRow;

  /**
   * Constructs a CollisionDetector object with a reference to
   * the GamePanel it is being used in.
   * @param gp The GamePanel object.
   */
  public CollisionDetector(GamePanel gp) {
    this.gp = gp;
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

    leftCol = leftX / GamePanel.TILE_SIZE;
    rightCol = rightX / GamePanel.TILE_SIZE;
    upRow = upY / GamePanel.TILE_SIZE;
    downRow = downY / GamePanel.TILE_SIZE;


    //checking the player's direction
    if (entity.direction == UP) {
      upRow = (upY - entity.speed) / GamePanel.TILE_SIZE;
      collide(entity);
    } else if (entity.direction == DOWN) {
      downRow = (downY + entity.speed) / GamePanel.TILE_SIZE;
      collide(entity);
    } else if (entity.direction == LEFT) {
      leftCol = (leftX - entity.speed) / GamePanel.TILE_SIZE;
      collide(entity);
    } else if (entity.direction == RIGHT) {
      rightCol = (rightX + entity.speed) / GamePanel.TILE_SIZE;
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
    int index = 999;
    for (int i = 0; i < gp.elements.length; i++) {
      if (gp.elements[i] != null) {

        //get entity/player's solid area position
        p.hitbox.x = p.worldX + p.hitbox.x;
        p.hitbox.y = p.worldY + p.hitbox.y;

        //get the object's solid area position
        gp.elements[i].getHitbox().x = gp.elements[i].getWorldX() + gp.elements[i].getHitbox().x;
        gp.elements[i].getHitbox().y = gp.elements[i].getWorldY() + gp.elements[i].getHitbox().y;

        if (p.direction == UP) {
          p.hitbox.y -= p.speed;
          if (p.hitbox.intersects(gp.elements[i].getHitbox())) {
            if (gp.elements[i].getCollision()) {
              p.collision = true;
            }
            if (player) {
              index = i;
            }
          }
        } else if (p.direction == DOWN) {
          p.hitbox.y += p.speed;
          if (p.hitbox.intersects(gp.elements[i].getHitbox())) {
            if (gp.elements[i].getCollision()) {
              p.collision = true;
            }
            if (player) {
              index = i;
            }
          }
        } else if (p.direction == LEFT) {
          p.hitbox.x -= p.speed;
          if (p.hitbox.intersects(gp.elements[i].getHitbox())) {
            if (gp.elements[i].getCollision()) {
              p.collision = true;
            }
            if (player) {
              index = i;
            }
          }
        } else if (p.direction == RIGHT) {
          p.hitbox.x += p.speed;
          if (p.hitbox.intersects(gp.elements[i].getHitbox())) {
            System.out.println("right collision");
            if (gp.elements[i].getCollision()) {
              p.collision = true;
            }
            if (player) {
              index = i;
            }
          }
        }
        p.hitbox.x = p.hitboxDefaultX;
        p.hitbox.y = p.hitboxDefaultY;

        gp.elements[i].getHitbox().x = 0;
        gp.elements[i].getHitbox().y = 0;
      }
    }
    return index;
  }
  
  
  public void checkCollide(int i, int index, boolean player, Player p) {
    if (gp.elements[i].getCollision()) {
      p.collision = true;
    }
    if (player) {
      index = i;
    }
  }


  // NPC and for Future use will include Monster
  public int checkEntityCollide(Entity entity, Entity[] target) {

    int index = 999;
    for (int i = 0; i < target.length; i++) {
      if (target[i] != null) {

        //get entity/player's solid area position
        entity.hitbox.x = entity.worldX + entity.hitbox.x;
        entity.hitbox.y = entity.worldY + entity.hitbox.y;

        //get teh object's solid area position
        target[i].hitbox.x = target[i].worldX + target[i].hitbox.x;
        target[i].hitbox.y = target[i].worldY + target[i].hitbox.y;

        if (entity.direction == UP) {
          entity.hitbox.y -= entity.speed;
          if (entity.hitbox.intersects(target[i].hitbox)) {
            System.out.println("up collision");
            entity.collision = true;
          }
        } else if (entity.direction == DOWN) {
          entity.hitbox.y += entity.speed;
          if (entity.hitbox.intersects(target[i].hitbox)) {
            System.out.println("down collision");
            entity.collision = true;
            index = i;
          }
        } else if (entity.direction == LEFT) {
          entity.hitbox.x -= entity.speed;
          if (entity.hitbox.intersects(target[i].hitbox)) {
            System.out.println("left collision");
            entity.collision = true;
            index = i;
          }
        } else if (entity.direction == RIGHT) {
          entity.hitbox.x += entity.speed;
          if (entity.hitbox.intersects(target[i].hitbox)) {
            System.out.println("right collision");
            entity.collision = true;
            index = i;
          }
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

    //get teh object's solid area position
    gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
    gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;

    if (entity.direction == UP) {
      entity.hitbox.y -= entity.speed;
    } else if (entity.direction == DOWN) {
      entity.hitbox.y += entity.speed;
    } else if (entity.direction == LEFT) {
      entity.hitbox.x -= entity.speed;
    } else if (entity.direction == RIGHT) {
      entity.hitbox.x += entity.speed;
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
