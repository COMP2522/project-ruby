package org.project.Objects;

import org.project.UI.GamePanel;
import org.project.Map.Positionable;

import java.awt.*;
import java.awt.image.BufferedImage;
import static org.project.SystemVariables.*;

/**
 * The Element class represents intractable objects in the game
 * that the player can interact with. Each element has an image,
 * a name, and a collision property indicating whether the
 * element can be collided with.
 *
 * @author Simrat Kaur
 * @version 2023-02-07
 */
public abstract class Element implements Positionable {

  //setting instance variables.
  private BufferedImage image;
  private int currentFrame = 0;
  private boolean collision = false;
  private int worldX, worldY;
  private final Rectangle hitbox = new Rectangle(0, 0, 46, 46);
  

  /**
   * draws the element on the game panel at its current position.
   *
   * @param g2 graphics object used to draw element
   * @param gp game panel on which the element is drawn
   */
  public void draw(Graphics2D g2, GamePanel gp) {
    int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
    int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();
    drawIfVisible(g2, gp, screenX, screenY);
  }

  /**
   * Draws the element on the game panel at its current position if it is visible.
   *
   * @param g2 graphics object used to draw element
   * @param gp game panel on which the element is drawn
   */
  public void drawIfVisible(Graphics2D g2, GamePanel gp, int screenX, int screenY) {
    if (((worldX + TILE_SIZE) > (gp.player.getWorldX() - gp.player.getScreenX())) &&
        ((worldX - TILE_SIZE) < (gp.player.getWorldX() + gp.player.getScreenX())) &&
        ((worldY + TILE_SIZE) > (gp.player.getWorldY() - gp.player.getScreenY())) &&
        ((worldY - TILE_SIZE) < (gp.player.getWorldY() + gp.player.getScreenY()))) {
      g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
    }
  }
  
  //setters and getters for the instance variables.
  public BufferedImage getImage() {
    return image;
  }
  public void setImage(BufferedImage image) {
    this.image = image;
  }
  public int getCurrentFrame() {
    return currentFrame;
  }
  public void setCurrentFrame(int currentFrame) {
    this.currentFrame = currentFrame;
  }
  public boolean getCollision() {
    return collision;
  }
  public void setCollision(boolean collision) {
    this.collision = collision;
  }
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
  public Rectangle getHitbox() {
    return hitbox;
  }
}
