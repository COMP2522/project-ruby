package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Fire extends Element {
  public BufferedImage[] fires;

  private long lastFrameTime;
  private int frameInterval = 150; // 150ms between each frame
  private int currentFrame = 0;

  public Fire() {
    name = "Fire";
    fires = new BufferedImage[4];
    try {
      for (int i = 0; i < fires.length; i++) {
        fires[i] = ImageIO.read(new FileInputStream("assets/mapData/objects/fire" + (i + 1) + ".png"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    image = fires[0];
    collision = true;
    lastFrameTime = System.currentTimeMillis();
  }

  @Override
  public void draw(Graphics2D g2, GamePanel gp) {
    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastFrameTime > frameInterval) {
      currentFrame = (currentFrame + 1) % fires.length;
      image = fires[currentFrame];
      lastFrameTime = currentTime;
    }
    g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
  }
}
