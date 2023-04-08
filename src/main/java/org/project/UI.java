package org.project;

import org.project.Objects.Ruby;

import java.awt.*;
import java.awt.image.BufferedImage;
import static org.project.SystemVariables.*;

/**
 * UI class that is responsible for drawing/displaying the player stats and other important messages.
 * This class is instantiable and manages UI components as a whole.
 *
 * @author Abhishek Chouhan
 */
public class UI {

  private static GamePanel gp; // JPanel attached to the game window
  private static Font font; // Arial font with 40 size
  
  // declaring all image components below
  BufferedImage keyImage;
  BufferedImage fullHeart, halfHeart, emptyHeart;

  private boolean displayMessage = false;

  private String message = "";

  private int messageFrameCounter;



  /**
   * Constructor for the UI class object that initializes all variables.
   * This object is used to draw over the game and display important stats.
   * @param gp the panel attached to game window in which stats are drawn.
   */
  public UI(GamePanel gp) {
    UI.gp = gp;
    UI.font = new Font("Arial", Font.PLAIN, 40);
    Ruby key = new Ruby();
    Life life = new Life();

    // Set all imageVariables to respective PNGs
    this.keyImage = key.getImage();
    this.fullHeart = life.getImage();
    this.halfHeart = life.halfLife;
    this.emptyHeart = life.emptyLife;

  }

  /**
   * this method is used to display message when an action is done by the player.
   * @param text specialized text for each action giving feedback/updating player on info.
   */
  public void showMessage(String text) {
    message = text;
    displayMessage = true;
  }

  /**
   * method to draw stats for the game.
   * @param g2 the panel attached to game window.
   */
  public void draw(Graphics2D g2) {
    g2.setFont(font);
    g2.setColor(Color.white);
    g2.drawImage(keyImage, TILE_SIZE/2, TILE_SIZE/2, TILE_SIZE, TILE_SIZE, null);
    g2.drawString("x " + gp.player.getCurrentRubies(), 74, 65);
    drawPlayerLife(g2);

    // popping message on window
    if (displayMessage) {
      g2.setFont(g2.getFont().deriveFont(30F));
      g2.drawString(message, TILE_SIZE/2, TILE_SIZE*5);
      messageFrameCounter++;
      // Amount of time a message displays
      int messageLength = 120;
      if (messageFrameCounter > messageLength) {
        messageFrameCounter = 0;
        displayMessage = false;
      }
    }
  }

  /**
   * used to draw the heart images, showing the status of current amount of lives.
   */
  public void drawPlayerLife(Graphics2D g2) {
    int x = TILE_SIZE * 10; // x coordinate of the lives display on screen
    int y = TILE_SIZE / 3; // y coordinate of the lives display on screen
    int i = 0; // counts the number of heart displayed already on screen respect to max lives

    // DRAW MAX LIFE
    while (i < gp.player.getLives() / 2) { // display half the max lives because each heart is 2 sub-lives
      g2.drawImage(emptyHeart, x, y, TILE_SIZE, TILE_SIZE, null);
      i++;
      x += TILE_SIZE;
    }

    // RESET after drawing emptyHearts (displaying full life but BLANK)
    x = TILE_SIZE * 10;
    i = 0;

    // DRAW CURRENT LIFE
    while(i < gp.player.getLives()) {
      g2.drawImage(halfHeart, x, y, TILE_SIZE, TILE_SIZE, null);
      i++;
      if (i < gp.player.getLives()) {
        g2.drawImage(fullHeart, x, y, TILE_SIZE, TILE_SIZE, null);
      }
      i++;
      x += TILE_SIZE;
    }

  }

}
