package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static org.project.Entity.directions.*;

public class Entity {

  GamePanel gp;
  public int worldX, worldY;
  public int screenX, screenY;
  public boolean collision = false;
  public int speed;

  public BufferedImage upR, upL, downR, downL, leftR, leftL, rightR, rightL;
  public enum directions {LEFT, RIGHT, UP, DOWN}
  public directions direction;

  public int spriteCounter = 0;
  public int spriteNum = 1;

  public int actionLockCounter = 0;
  public Rectangle solidArea;
  public int solidAreaDefaultX, solidAreaDefaultY;
  
  public Entity(GamePanel gp) {
    this.gp = gp;
  }

  public void setAction() {}

  public void update() {
    setAction();
    collision = false;
    gp.cDetector.checkTile(this);

    //checking tile collision
    collision = false;
    gp.cDetector.checkTile(this);
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
    if(spriteCounter > 14) {
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
