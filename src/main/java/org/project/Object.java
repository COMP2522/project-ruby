package org.project;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Object {
  public BufferedImage image;
  public int currentFrame = 0;
  public String name;
  public boolean collision = false;
  public int worldX, worldY;
  
  public Rectangle solidArea = new Rectangle(0,0,48,48);
  public int solidAreaDefaultX = 0;
  public int solidAreaDefaultY = 0;
  
  
  public void draw(Graphics2D g2, GamePanel gp) {
  
    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;
  
    if (worldX + GamePanel.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
          worldX - GamePanel.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
          worldY + GamePanel.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
          worldY - GamePanel.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
      g2.drawImage(image, screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }
  
  
    if (name != null && name.equals("fire")) {
      g2.drawImage(gp.objects[5 + currentFrame].image, screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    } else {
      g2.drawImage(image, screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }
  }
}
