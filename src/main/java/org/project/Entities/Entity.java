package org.project.Entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import org.project.Map.Positionable;
import org.project.ui.GamePanel;

import static org.project.SystemVariables.*;

/**
 * Abstract class for an entity that can move across the map.
 *
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
  protected Directions direction;

  // Entity state variables
  protected boolean collision = false; // is colliding = false
  protected boolean invincible = false; // is invincible = false
  protected boolean onPath = false;
  protected int invincibleCounter = 0;
  // -----------------------------------------------------------//

  public Rectangle hitbox; // Entity hitbox to check for collision (and damage in player's case)
  public int hitboxDefaultX, hitboxDefaultY;

  // Sprites (or Images) for different instances of Entity
  protected BufferedImage upR, upL, downR, downL, leftR, leftL, rightR, rightL;
  protected final int spriteMax = 20; // Should only update every 14 frames, not every frame
  protected final int indexMax = 999; // Max number of elements that can be displayed in tile array

  // below two variables are only being accessed by the server
  public int spriteCounter = 0; // var to count how many instances of a sprite have been drawn
  public int spriteNum = 1; // the current sprite to be displayed
  protected int actionLockCounter = 0;
  // ---------------------All Animation related stuff above ------------------------///

  protected int type; // 0 = player, 1 = npc, 2 = monster, 3 = projectile

  /**
   * Constructor for Entity.
   *
   * @param gp GamePanel instance being accessed by all Entities
   */
  public Entity(GamePanel gp) {
    this.gp = gp;
    // default location to spawn entities to (does not really matter
    // as this is being overridden in specific constructors of subclasses
    this.hitbox = new Rectangle(10, 10, 28, 38);
  }

  /**
   * Checks if Entity has collided with a tile.
   */
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

  /**
   * Updates the entity depending on its current action.
   */
  public void update() {
    setAction();
    checkCollision();

    // update position of entity if it is not colliding with anything
    if (!collision) {
      switch (direction) {
        case LEFT -> worldX -= speed;
        case RIGHT -> worldX += speed;
        case UP -> worldY -= speed;
        default -> worldY += speed;
      }
    }

    // update Entity sprite after a set counter
    // spriteMax is actually the value after which sprite is switched
    // say spriteMax was 15, then the next sprite would be drawn after
    // the previous sprite has been drawn for 15 times already
    spriteCounter++;
    if (spriteCounter > spriteMax) {
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
   *
   * @param g2 Graphics to draw
   */
  public void draw(Graphics2D g2) {
    BufferedImage image = null;
    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    // draw an entity if camera is in focus, that is the player is nearby the entity
    //  otherwise save processing and conserve resources
    // (the entities are still updated, just not drawn out of window)
    if (worldX + TILE_SIZE > gp.player.worldX - gp.player.screenX
        && worldX - TILE_SIZE < gp.player.worldX + gp.player.screenX
        && worldY + TILE_SIZE > gp.player.worldY - gp.player.screenY
        && worldY - TILE_SIZE < gp.player.worldY + gp.player.screenY) {
      switch (direction) {
        case UP -> {
          if (spriteNum == 1) {
            image = upR;
          }
          if (spriteNum == 2) {
            image = upL;
          }
        }
        case DOWN -> {
          if (spriteNum == 1) {
            image = downR;
          }
          if (spriteNum == 2) {
            image = downL;
          }
        }
        case LEFT -> {
          if (spriteNum == 1) {
            image = leftR;
          }
          if (spriteNum == 2) {
            image = leftL;
          }
        }
        case RIGHT -> {
          if (spriteNum == 1) {
            image = rightR;
          }
          if (spriteNum == 2) {
            image = rightL;
          }
        }
        default -> {
        }
      }
      // if entity is invincible, draw it as transparent for few seconds
      if (invincible) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
      }
      g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
      // Draw hitbox for debugging purposes
      // g2.setColor(Color.red);
      // g2.drawRect(screenX + hitboxDefaultX,screenY+ hitboxDefaultY, hitbox.width, hitbox.height);
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
  }

  // bunch of Getters and setters
  public int getWorldX() {
    return worldX;
  }

  public void setWorldX(int worldX) {
    this.worldX = worldX;
  }

  public int getWorldY() {
    return worldY;
  }

  public void setWorldY(int worldY) {
    this.worldY = worldY;
  }

  public int getScreenX() {
    return screenX;
  }

  public int getScreenY() {
    return screenY;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int newSpeed) {
    speed = newSpeed;
  }

  public Directions peekDirection() {
    return direction;
  }

  public void changeDirection(Directions direction) {
    this.direction = direction;
  }

  public void setCollided(boolean colStat) {
    collision = colStat;
  }

  public abstract void setAction();

}
