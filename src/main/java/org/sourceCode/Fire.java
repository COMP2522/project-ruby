package org.sourceCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class representing the fire tile.
 *
 * This class extends the Tile class and inherits its fields and methods.
 * It adds a new constructor and sets the collision to true.
 *
 * @author Nathan Bartyuk
 * @version 2023-03-25
 */
public class Fire extends Tile {
  private Image[] fires;
  private int currentFrame;
  private int frameDelay;
  private int frameCounter;
  public Fire(int x, int y) throws IOException {
    this.x = x;
    this.y = y;
    fires[0] = ImageIO.read(new File("assets/mapData/images/fire1.png"));
    fires[1] = ImageIO.read(new File("assets/mapData/images/fire2.png"));
    fires[2] = ImageIO.read(new File("assets/mapData/images/fire3.png"));
    fires[3] = ImageIO.read(new File("assets/mapData/images/fire4.png"));
    currentFrame = 0;
    frameDelay = 10;
    frameCounter = 0;
  }

  public void draw(Graphics2D g2) {
    g2.drawImage(fires[currentFrame], x, y, null);
  }

  public void update() {
    frameCounter++;
    if (frameCounter >= frameDelay) {
      currentFrame++;
      if (currentFrame >= fires.length) {
        currentFrame = 0;
      }
      frameCounter = 0;
      frameDelay = (int) (Math.random() * 10) + 5; // random delay between 5 and 14 frames
    }
  }
}

