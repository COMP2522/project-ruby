package org.project.Entities;

import org.project.UI.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import static org.project.SystemVariables.*;

/**
 * Monster class for Beast is slated to be in the same vein as NPC.
 * The collision for this end will revolve around the player's lives.
 * Entity class is extended by this monster class, and al following monster classes.
 * Entity, GamePanel, ObjectHandler and CollisionDetector will all require code
 * May use pathfinding algorithm if I can develop it to be functional.
 * Update the coordinates for future use to have it in different sections of the map to guard rubies.
 *
 * @author Amrit Singh
 * @version 2023-03-27
 */
public class Monster extends Entity {
  
  /**
   * Constructs the Monster object
   * @param gp GamePanel it belongs to
   * @param posX Monster position X
   * @param posY Monster position Y
   */
  public Monster(GamePanel gp, int posX, int posY) {
    super(gp);
    type = 2;
    speed = 2;
    hitbox = new Rectangle();
    hitbox.x = 8;
    hitbox.y = 8;
    hitboxDefaultX = hitbox.x;
    hitboxDefaultY = hitbox.y;
    hitbox.width = 32;
    hitbox.height = 32;
    this.worldX = posX;
    this.worldY = posY;
    direction = directions.DOWN;
    getImage();
  }
  
  /** Gets the image for each sprite */
  public void getImage() {
    try {
      downR = ImageIO.read(new FileInputStream("assets/monsters/Slime_Contract.png"));
      downL = ImageIO.read(new FileInputStream("assets/monsters/Slime_Relaxed.png"));
      upR = ImageIO.read(new FileInputStream("assets/monsters/Slime_Contract.png"));
      upL = ImageIO.read(new FileInputStream("assets/monsters/Slime_Relaxed.png"));
      leftR = ImageIO.read(new FileInputStream("assets/monsters/Slime_Contract.png"));
      leftL = ImageIO.read(new FileInputStream("assets/monsters/Slime_Relaxed.png"));
      rightR = ImageIO.read(new FileInputStream("assets/monsters/Slime_Contract.png"));
      rightL = ImageIO.read(new FileInputStream("assets/monsters/Slime_Relaxed.png"));
    } catch (IOException e) {
      System.out.println("Image can't be read ...");
      e.printStackTrace();
    }
  }

  /**
   * method to set the behaviour of the monsters/slime.
   * The searchPath helper function determines the path the entity should take to the player.
   * The searchPath utilizes the pFinder (PathFinder class instance) which in turn utilizes
   * the concept of A* path finding algorithm, a popular path finding algorithm in games.
   */
  @Override
  public void setAction() {
    int goalCol = (gp.player.worldX + gp.player.hitbox.x) / TILE_SIZE;
    int goalRow = (gp.player.worldY + gp.player.hitbox.y) / TILE_SIZE;
    searchPath(goalCol, goalRow);
  }

  /**
   * Determines the path the monster should take to the player
   * @param goalCol Column of the goal location
   * @param goalRow Row of the goal location
   */
  public void searchPath(int goalCol, int goalRow){

    int startCol = (worldX + hitbox.x) / TILE_SIZE;
    int startRow = (worldY + hitbox.y) / TILE_SIZE;

    gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

    if(gp.pFinder.search()) {
      // Next worldX and worldY
      int nextX = gp.pFinder.pathList.get(0).col * TILE_SIZE;
      int nextY = gp.pFinder.pathList.get(0).row * TILE_SIZE;

      // Entity's hitbox positioning on the world map
      int enLeftX = worldX + hitbox.x;
      int enRightX = worldX + hitbox.x + hitbox.width;
      int enTopY = worldY + hitbox.y;
      int enBottomY = worldY + hitbox.y + hitbox.height;

      if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + TILE_SIZE){
        direction = directions.UP;
      } else if(enBottomY < nextY && enLeftX >= nextX && enRightX < nextX + TILE_SIZE){
        direction = directions.DOWN;
      } else if(enTopY >= nextY && enBottomY < nextY + TILE_SIZE) {
        if(enLeftX > nextX) {
          direction = directions.LEFT;
        } else if(enLeftX < nextX) {
          direction = directions.RIGHT;
        }
      } else if(enTopY > nextY && enLeftX > nextX) {
        direction = directions.UP;
        checkCollision();
        if(collision) {
          direction = directions.LEFT;
        }
      } else if(enTopY > nextY && enLeftX < nextX) {
        direction = directions.UP;
        checkCollision();
        if(collision) {
          direction = directions.RIGHT;
        }
      } else if(enTopY < nextY && enLeftX > nextX) {
        direction = directions.DOWN;
        checkCollision();
        if(collision) {
          direction = directions.LEFT;
        }
      } else if(enTopY < nextY && enLeftX < nextX) {
        direction = directions.DOWN;
        checkCollision();
        if(collision) {
          direction = directions.RIGHT;
        }
      }

      int nextCol = gp.pFinder.pathList.get(0).col;
      int nextRow = gp.pFinder.pathList.get(0).row;
      if(nextCol == goalCol && nextRow == goalRow) {
        onPath = false;
      }
    }
  }

}

