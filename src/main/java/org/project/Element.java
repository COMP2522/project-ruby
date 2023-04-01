package org.project;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Element class represents intractable objects in the game
 * that the player can interact with. Each element has an image,
 * a name, and a collision property indicating whether the
 * element can be collided with.
 *
 * @author Nathan Bartyuk, Simrat Kaur, Abhishek Chouhan, Amrit Jhatu, Greg
 * @version 2023-02-07
 */
public abstract class Element {
  //setting instance variables.
  private BufferedImage image;
  private int currentFrame = 0;
  private String name;
  private boolean collision = false;
  private int worldX, worldY;
  private final Rectangle solidArea = new Rectangle(0, 0, 48, 48);

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public Rectangle getSolidArea() {
    return solidArea;
  }


  /**
   * draws the element on the game panel at its current position.
   *
   * @param g2 graphics object used to draw element
   * @param gp game panel on which the element is drawn
   */
  public void draw(Graphics2D g2, GamePanel gp) {

    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    drawIfVisible(g2, gp, screenX, screenY);
  }

  /**
   * Draws the element on the game panel at its current position if it is visible.
   *
   * @param g2 graphics object used to draw element
   * @param gp game panel on which the element is drawn
   * @return
   */
  public void drawIfVisible(Graphics2D g2, GamePanel gp, int screenX, int screenY) {
    if (((worldX + GamePanel.TILE_SIZE) > (gp.player.worldX - gp.player.screenX)) &&
        ((worldX - GamePanel.TILE_SIZE) < (gp.player.worldX + gp.player.screenX)) &&
        ((worldY + GamePanel.TILE_SIZE) > (gp.player.worldY - gp.player.screenY)) &&
        ((worldY - GamePanel.TILE_SIZE) < (gp.player.worldY + gp.player.screenY))) {
      g2.drawImage(image, screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }
  }
}
