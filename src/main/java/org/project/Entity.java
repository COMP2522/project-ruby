package org.project;

import java.awt.*;
import java.awt.image.BufferedImage;
import static org.project.SystemVariables.*;

/**
 * Abstract class for an entity that can move across the map.
 * @author Team Ruby
 * @version 2023-04-04
 */
public abstract class Entity implements Positionable {

  public GamePanel gp;
  public int worldX, worldY;
  public int screenX, screenY;
  public boolean collision = false;
  public boolean invincible = false;
  public boolean onPath = false;
  public int invincibleCounter = 0;
  public int speed;

  public BufferedImage upR, upL, downR, downL, leftR, leftL, rightR, rightL;
  public directions direction;
  
  public final int spriteMax = 20; // Should only update every 14 frames, not every frame
  public final int indexMax = 999; // Max number of elements that can be displayed in tile array
  public int spriteCounter = 0;
  public int spriteNum = 1;
  public int actionLockCounter = 0;
  
  public Rectangle hitbox;
  public int hitboxDefaultX, hitboxDefaultY;
  public int type; // 0 = player, 1 = npc, 2 = monster, 3 = projectile
  
  public Entity(GamePanel gp) {
    this.gp = gp;
  }
  
  /** Checks if Entity has collided with a tile. */
  public void checkCollision() {
    collision = false;
    gp.cDetector.checkTile(this);
    gp.cDetector.checkPlayerCollide(this);
    boolean contactPlayer = gp.cDetector.checkPlayerCollide(this);
    if (this.type == 2 && contactPlayer && !gp.player.invincible) {
      gp.player.currentLives -= 1;
      gp.player.invincible = true;
    }
  }
  
  /** Updates the entity depending on its current action. */
  public void update() {
    setAction();
    checkCollision();
    
    if (!collision) {
      switch (direction) {
        case LEFT -> worldX -= speed;
        case RIGHT -> worldX += speed;
        case UP -> worldY -= speed;
        default -> worldY += speed;
      }
    }
    
    spriteCounter++;
    if(spriteCounter > spriteMax) {
      if (spriteNum == 1) {
        spriteNum = 2;
      } else if (spriteNum == 2) {
        spriteNum = 1;
      }
      spriteCounter = 0;
    }
  }
  
  /**
   * Draws the entity on the map, depending on if it is in view.
   * @param g2 Graphics to draw
   */
  public void draw(Graphics2D g2) {
    BufferedImage image = null;
    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    if (worldX + TILE_SIZE > gp.player.worldX - gp.player.screenX &&
      worldX - TILE_SIZE < gp.player.worldX + gp.player.screenX &&
      worldY + TILE_SIZE > gp.player.worldY - gp.player.screenY &&
      worldY - TILE_SIZE < gp.player.worldY + gp.player.screenY) {
      switch (direction) {
        case UP -> {
          if (spriteNum == 1) {image = upR;}
          if (spriteNum == 2) {image = upL;}
        }
        case DOWN -> {
          if (spriteNum == 1) {image = downR;}
          if (spriteNum == 2) {image = downL;}
        }
        case LEFT -> {
          if (spriteNum == 1) {image = leftR;}
          if (spriteNum == 2) {image = leftL;}
        }
        case RIGHT -> {
          if (spriteNum == 1) {image = rightR;}
          if (spriteNum == 2) {image = rightL;}
        }
        default -> {}
      }
      if (invincible) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
      }
      g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
      // Draw hitbox for debugging purposes
      // g2.setColor(Color.red);
      // g2.drawRect(screenX + hitboxDefaultX, screenY + hitboxDefaultY, hitbox.width, hitbox.height);
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
  }
  
  /**
   * Determines the path the entity should take
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

      // Entity's hitbox positioning
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
  
  // Getter and setters for worldX and worldY below.
  public int getWorldX() { return worldX; }
  public void setWorldX(int worldX) { this.worldX = worldX; }
  public int getWorldY() { return worldY; }
  public void setWorldY(int worldY) { this.worldY = worldY; }
  public abstract void setAction();
}
