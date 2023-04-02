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
    int leftX = entity.worldX + entity.solidArea.x;
    int rightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
    int upY = entity.worldY + entity.solidArea.y;
    int downY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

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
        p.solidArea.x = p.worldX + p.solidArea.x;
        p.solidArea.y = p.worldY + p.solidArea.y;

        //get the object's solid area position
        gp.elements[i].getSolidArea().x = gp.elements[i].getWorldX() + gp.elements[i].getSolidArea().x;
        gp.elements[i].getSolidArea().y = gp.elements[i].getWorldY() + gp.elements[i].getSolidArea().y;

        if (p.direction == UP) {
          p.solidArea.y -= p.speed;
          if (p.solidArea.intersects(gp.elements[i].getSolidArea())) {
            if (gp.elements[i].getCollision()) {
              p.collision = true;
            }
            if (player) {
              index = i;
            }
          }
        } else if (p.direction == DOWN) {
          p.solidArea.y += p.speed;
          if (p.solidArea.intersects(gp.elements[i].getSolidArea())) {
            if (gp.elements[i].getCollision()) {
              p.collision = true;
            }
            if (player) {
              index = i;
            }
          }
        } else if (p.direction == LEFT) {
          p.solidArea.x -= p.speed;
          if (p.solidArea.intersects(gp.elements[i].getSolidArea())) {
            if (gp.elements[i].getCollision()) {
              p.collision = true;
            }
            if (player) {
              index = i;
            }
          }
        } else if (p.direction == RIGHT) {
          p.solidArea.x += p.speed;
          if (p.solidArea.intersects(gp.elements[i].getSolidArea())) {
            System.out.println("right collision");
            if (gp.elements[i].getCollision()) {
              p.collision = true;
            }
            if (player) {
              index = i;
            }
          }
        }
        p.solidArea.x = p.solidAreaDefaultX;
        p.solidArea.y = p.solidAreaDefaultY;

        gp.elements[i].getSolidArea().x = 0;
        gp.elements[i].getSolidArea().y = 0;
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
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        //get teh object's solid area position
        target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
        target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

        if (entity.direction == UP) {
          entity.solidArea.y -= entity.speed;
          if (entity.solidArea.intersects(target[i].solidArea)) {
            System.out.println("up collision");
            entity.collision = true;
          }
        } else if (entity.direction == DOWN) {
          entity.solidArea.y += entity.speed;
          if (entity.solidArea.intersects(target[i].solidArea)) {
            System.out.println("down collision");
            entity.collision = true;
            index = i;
          }
        } else if (entity.direction == LEFT) {
          entity.solidArea.x -= entity.speed;
          if (entity.solidArea.intersects(target[i].solidArea)) {
            System.out.println("left collision");
            entity.collision = true;
            index = i;
          }
        } else if (entity.direction == RIGHT) {
          entity.solidArea.x += entity.speed;
          if (entity.solidArea.intersects(target[i].solidArea)) {
            System.out.println("right collision");
            entity.collision = true;
            index = i;
          }
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;

        target[i].solidArea.x = target[i].solidAreaDefaultX;
        target[i].solidArea.y = target[i].solidAreaDefaultY;
      }
    }
    return index;
  }

  public boolean checkPlayerCollide( Entity entity) {

    boolean contactPlayer = false;

    //get entity/player's solid area position
    entity.solidArea.x = entity.worldX + entity.solidArea.x;
    entity.solidArea.y = entity.worldY + entity.solidArea.y;

    //get teh object's solid area position
    gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
    gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

    if (entity.direction == UP) {
      entity.solidArea.y -= entity.speed;
      if (entity.solidArea.intersects(gp.player.solidArea)) {
        System.out.println("up collision");
        entity.collision = true;
        contactPlayer = true;
      }
    } else if (entity.direction == DOWN) {
      entity.solidArea.y += entity.speed;
      if (entity.solidArea.intersects(gp.player.solidArea)) {
        System.out.println("down collision");
        entity.collision = true;
        contactPlayer = true;
      }
    } else if (entity.direction == LEFT) {
      entity.solidArea.x -= entity.speed;
      if (entity.solidArea.intersects(gp.player.solidArea)) {
        System.out.println("left collision");
        entity.collision = true;
        contactPlayer = true;
      }
    } else if (entity.direction == RIGHT) {
      entity.solidArea.x += entity.speed;
      if (entity.solidArea.intersects(gp.player.solidArea)) {
        System.out.println("right collision");
        entity.collision = true;
        contactPlayer = true;
      }
    }
    entity.solidArea.x = entity.solidAreaDefaultX;
    entity.solidArea.y = entity.solidAreaDefaultY;

    gp.player.solidArea.x = gp.player.solidAreaDefaultX;
    gp.player.solidArea.y = gp.player.solidAreaDefaultY;

    return contactPlayer;
  }

}
