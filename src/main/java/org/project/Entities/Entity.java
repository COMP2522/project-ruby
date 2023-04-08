package org.project.Entities;

import org.project.GamePanel;
import org.project.Positionable;

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

  // Entity positional, and other variables
  protected int worldX, worldY;   // Coordinates on the 50 * 50 world map
  protected int screenX, screenY;  // Coordinates on the 16 * 12 screen
  protected int speed;
  protected directions direction;

  // Entity state variables
  protected boolean collision = false; // is colliding = false
  protected boolean invincible = false; // is invincible = false
  protected boolean onPath = false;
  protected int invincibleCounter = 0;

  // Sprites (or Images) for different instances of Entity
  protected BufferedImage upR, upL, downR, downL, leftR, leftL, rightR, rightL;

  public final int spriteMax = 20; // Should only update every 14 frames, not every frame
  public final int indexMax = 999; // Max number of elements that can be displayed in tile array
  public int spriteCounter = 0; // variable to count exactly how many instances of a single sprite have already been drawn
  public int spriteNum = 1; // the current sprite to be displayed
  public int actionLockCounter = 0;
  
  public Rectangle hitbox;
  public int hitboxDefaultX, hitboxDefaultY;
  protected int type; // 0 = player, 1 = npc, 2 = monster, 3 = projectile
  
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
  public int getScreenX() { return screenX; }
  public int getScreenY() { return screenY; }
  public int getSpeed() {return speed;}
  public void setSpeed(int newSpeed) {speed = newSpeed;}
  public directions peekDirection() {return direction;}
  public void changeDirection(directions direction) {this.direction = direction;}
  public void setCollided(boolean colStat) {collision = colStat;}
  public abstract void setAction();

}
