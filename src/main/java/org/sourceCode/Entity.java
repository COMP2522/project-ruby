package org.sourceCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

  GamePanel gp;
  public int x, y;
  public int speed;

  public BufferedImage upR, upL, downR, downL, leftR, leftL, rightR, rightL;

  public enum directions {LEFT, RIGHT, UP, DOWN}

  public directions currentDirection;

  public int spriteCounter = 0;
  public int spriteNum = 1;

  public Rectangle solidArea = new Rectangle(8, 8, 32, 32);

  public int solidAreaDefaultX, solidAreaDefaultY;

  public boolean collisionOn = false;

  public Entity(GamePanel gp) {
    this.gp = gp;
  }

  public void setAction() {}

  public void update() {
    setAction();

    collisionOn = false;
    gp.cChecker.checkTile(this);
  }

  public void draw(Graphics2D g2, GamePanel gp) {
    BufferedImage image = null;

    switch(currentDirection) {
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
    g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
  }


  public BufferedImage setup(String imagePath){
    BufferedImage image = null;

    try {
      image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
    } catch(IOException e) {
      e.printStackTrace();
    }
    return image;
  }

}
