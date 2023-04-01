package org.project;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * UI class that is responsible for drawing/displaying the player stats and other important messages.
 * This class is instantiable and manages UI components as a whole.
 *
 * @author Abhishek Chouhan
 */
public class UI {

  // JPanel attached to the game window
  private GamePanel gp;

  // Arial font with 40 size
  private Font arial_40;

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
    this.gp = gp;
    // initialize Window's font to Arial type and 40 size
    this.arial_40 = new Font("Arial", Font.PLAIN, 40);
    // Creating HUD Objects
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
    g2.setFont(arial_40);
    g2.setColor(Color.white);
    g2.drawImage(keyImage, gp.TILE_SIZE / 2, gp.TILE_SIZE/2, gp.TILE_SIZE, gp.TILE_SIZE, null);
    // 50, 50 are the (x, y) coordinates of the box displaying number of rubies
    g2.drawString("x " + gp.player.getCurrentRubies(), 74, 65);

    drawPlayerLife(g2);

    // popping message on window
    if (displayMessage) {
      g2.setFont(g2.getFont().deriveFont(30F));
      g2.drawString(message, gp.TILE_SIZE/2, gp.TILE_SIZE*5);
      messageFrameCounter++;

      // when message is drawn 120 times, it would have been drawn/displayed for 2 seconds
      // how? Our game functions at 60 frames per second, only 60 frames drawn each second
      // so when 120 frames have been drawn, message has stayed for a total of 2 seconds on screen
      if (messageFrameCounter > 120) {
        messageFrameCounter = 0;
        displayMessage = false;
      }
    }
  }

  /**
   * used to draw the heart images, showing the status of current amount of lives.
   */
  public void drawPlayerLife(Graphics2D g2) {
    int x = gp.TILE_SIZE * 10; // x coordinate of the lives display on screen
    int y = gp.TILE_SIZE / 3; // y coordinate of the lives display on screen
    int i = 0; // counts the number of heart displayed already on screen respect to max lives

    // DRAW MAX LIFE
    while (i < gp.player.getLives() / 2) { // display half the max lives because each heart is 2 sub-lives
      g2.drawImage(emptyHeart, x, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
      i++;
      x += gp.TILE_SIZE;
    }

    // RESET after drawing emptyHearts (displaying full life but BLANK)
    x = gp.TILE_SIZE * 10;
    y = gp.TILE_SIZE / 3;
    i = 0;

    // DRAW CURRENT LIFE
    while(i < gp.player.getLives()) {
      g2.drawImage(halfHeart, x, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
      i++;
      if (i < gp.player.getLives()) {
        g2.drawImage(fullHeart, x, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
      }
      i++;
      x += gp.TILE_SIZE;
    }

  }

}
