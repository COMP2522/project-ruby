package org.project.Objects;

import org.project.ui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import static org.project.SystemVariables.*;

/**
 * Defines the Fire element that is not collidable and the player
 * is supposed to die when they come in contact with fire.
 *
 * @author Simrat Kaur
 * @version 2023-02-07
 */
public class Fire extends Element {
  private final BufferedImage[] fires;
  private long lastFrameTime;

  /**
   * Constructs a Fire object and sets its name, image, and collision properties.
   * Also sets up the animation of the Fire element.
   */
  public Fire() {
    // creating an array of four frames for the animation
    fires = new BufferedImage[4];
    try {
      for (int i = 0; i < fires.length; i++) {
        fires[i] = ImageIO.read(new FileInputStream("assets/data/objects/fire" + (i + 1) + ".png"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    setImage(fires[0]);
    setCollision(false);
    lastFrameTime = System.currentTimeMillis();
  }

  /**
   * Draws the Fire element's current frame image on the GamePanel.
   * Also updates the current frame of the Fire element's animation.
   *
   * @param g2 the Graphics2D object to be drawn on
   * @param gp the GamePanel object where the Fire element is being drawn
   */
  @Override
  public void draw(Graphics2D g2, GamePanel gp) {

    //calculates the X and Y position of the Fire element on the screen.
    int screenX = getWorldX() - gp.player.getWorldX() + gp.player.getScreenX();
    int screenY = getWorldY() - gp.player.getWorldY() + gp.player.getScreenY();

    long currentTime = System.currentTimeMillis(); // gets the current time
    int frameInterval = 150;     // the time between each frame in milliseconds

    /*
    Updates the current frame of the Fire element's animation if enough time has
    passed since the last frame, and sets the Fire element's image to the current
    frame of the animation.
    */
    if (currentTime - lastFrameTime > frameInterval) {
      setCurrentFrame((getCurrentFrame() + 1) % fires.length);
      setImage(fires[getCurrentFrame()]);
      lastFrameTime = currentTime;
    }
    int tileSize = TILE_SIZE;
    g2.drawImage(getImage(), screenX, screenY, tileSize, tileSize, null);
  }
}
