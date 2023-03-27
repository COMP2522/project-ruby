package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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

  public Rectangle solidArea = new Rectangle(8, 8, 32, 32);

  public int solidAreaDefaultX, solidAreaDefaultY;

  public Entity(GamePanel gp) {
    this.gp = gp;
  }

  public void setAction() {}

  public void update() {
    setAction();

    collision = false;
    gp.cChecker.checkTile(this);
  }

  public void draw(Graphics2D g2) {
    BufferedImage image = null;

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
