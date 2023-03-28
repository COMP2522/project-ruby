package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Defines the Fire element that is not collidable and the player
 * is supposed to die when they come in contact with fire.
 *
 * @author Nathan Bartyuk, Simrat Kaur, Abhishek Chouhan, Amrit Jhatu, Greg
 * @version 2023-02-07
 */
public class Fire extends Element {
  public BufferedImage[] fires;

  private long lastFrameTime;
  private int frameInterval = 150; // 150ms between each frame
  private int currentFrame = 0;

  /**
   * Constructs a Fire object and sets its name, image, and collision properties.
   * Also sets up the animation of the Fire element.
   */
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

  /**
   * Draws the Fire element's current frame image on the GamePanel.
   * Also updates the current frame of the Fire element's animation.
   * @param g2 the Graphics2D object to be drawn on
   * @param gp the GamePanel object where the Fire element is being drawn
   */
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
