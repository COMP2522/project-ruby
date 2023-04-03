package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static org.project.Entity.directions.*;

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
  public enum directions {LEFT, RIGHT, UP, DOWN}
  public directions direction;
  
  public final int spriteLimit = 14; // Should only update every 14 frames, not every frame
  public final int indexMax = 999; // Max number of elements that can be displayed in tile array
  public int spriteCounter = 0;
  public int spriteNum = 1;

  public int actionLockCounter = 0;
  public Rectangle hitbox;
  public int hitboxDefaultX, hitboxDefaultY;
  
  // Getter and setters for worldX and worldY below.
  public int getWorldX() { return worldX; }
  public void setWorldX(int worldX) { this.worldX = worldX; }
  public int getWorldY() { return worldY; }
  public void setWorldY(int worldY) { this.worldY = worldY; }
  
  
  public int type; // 0 = player, 1 = npc, 2 = monster, 3 = projectile
  
  
  public void setAction() {
    actionLockCounter++;
    if (actionLockCounter == 120) {
      Random random = new Random();
      int i = random.nextInt(100) + 1; // picks up a number from 1 to 100
      
      switch(i/25) {
        case 0:
          direction = directions.UP;
          break;
        case 1:
          direction = directions.DOWN;
          break;
        case 2:
          direction = directions.LEFT;
          break;
        case 3:
          direction = directions.RIGHT;
          break;
      }
      actionLockCounter = 0;
    }
  }
  
  public Entity(GamePanel gp) {
    this.gp = gp;
  }

  public void update() {
    setAction();

    //checking tile collision
    collision = false;
    gp.cDetector.checkTile(this);
    gp.cDetector.checkPlayerCollide(this);
    boolean contactPlayer = gp.cDetector.checkPlayerCollide(this);
    if (this.type == 2 && contactPlayer && !gp.player.invincible) {
      gp.player.currentLives -= 1;
      gp.player.invincible = true;
    }
    
    if (!collision) {
      if (direction == LEFT) {
        worldX -= speed;
      } else if (direction == RIGHT) {
        worldX += speed;
      } else if (direction == UP) {
        worldY -= speed;
      } else {
        worldY += speed;
      }
    }
    
    spriteCounter++;
    if(spriteCounter > 20) {
      if (spriteNum == 1) {
        spriteNum = 2;
      } else if (spriteNum == 2) {
        spriteNum = 1;
      }
      spriteCounter = 0;
    }
  }

  public void draw(Graphics2D g2) {
    BufferedImage image = null;
    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    if (worldX + GamePanel.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
      worldX - GamePanel.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
      worldY + GamePanel.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
      worldY - GamePanel.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
      switch(direction) {
        case UP:
          if (spriteNum == 1) {image = upR;}
          if (spriteNum == 2) {image = upL;}
          break;
        case DOWN:
          if (spriteNum == 1) {image = downR;}
          if (spriteNum == 2) {image = downL;}
          break;
        case LEFT:
          if (spriteNum == 1) {image = leftR;}
          if (spriteNum == 2) {image = leftL;}
          break;
        case RIGHT:
          if (spriteNum == 1) {image = rightR;}
          if (spriteNum == 2) {image = rightL;}
          break;
        default:
          break;
      }
      g2.drawImage(image, screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }
  }


  public BufferedImage setup(String imagePath){
    BufferedImage image = null;
    try {
      image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
    } catch(IOException e) {
      e.printStackTrace();
    }
    return image;
  }

}
