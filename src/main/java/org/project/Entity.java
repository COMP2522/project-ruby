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

  // gamePanel instance being accessed by all Entities
  public GamePanel gp;

  // Coordinates on the 50 * 50 world map and on the 16 * 12 screen respectively
  private int worldX, worldY;
  public int screenX, screenY;

  // state variables
  public boolean collision = false; // is colliding = false
  protected boolean invincible = false; // is invincible = false
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
      gp.player.setLives(gp.player.getLives() - 1); // decrement player lives
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
    int screenX = worldX - gp.player.getWorldX() + gp.player.screenX;
    int screenY = worldY - gp.player.getWorldY() + gp.player.screenY;

    if (worldX + TILE_SIZE > gp.player.getWorldX() - gp.player.screenX &&
      worldX - TILE_SIZE < gp.player.getWorldX() + gp.player.screenX &&
      worldY + TILE_SIZE > gp.player.getWorldY() - gp.player.screenY &&
      worldY - TILE_SIZE < gp.player.getWorldY() + gp.player.screenY) {
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
      // if entity is invincible, draw it as transparent for few seconds
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

  // Getter and setters for worldX and worldY below.
  public int getWorldX() { return worldX; }
  public void setWorldX(int worldX) { this.worldX = worldX; }
  public int getWorldY() { return worldY; }
  public void setWorldY(int worldY) { this.worldY = worldY; }
  public abstract void setAction();

}
