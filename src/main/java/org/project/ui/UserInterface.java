package org.project.ui;

import static org.project.SystemVariables.TILE_SIZE;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.project.Objects.Ruby;

/**
 * ui class that is responsible for drawing/displaying
 * the player stats and other important messages.
 * This class is instantiable and manages ui components as a whole.
 *
 * @author Abhishek Chouhan
 */
public class UserInterface {

  /**
   * The JPanel attached to the game window.
   */
  private static GamePanel gp;

  /**
   * The Arial font with a size of 40.
   */
  private static Font font;

  /**
   * The image component for the key.
   */
  BufferedImage keyImage;

  /**
   * The image component for the full heart.
   */
  BufferedImage fullHeart;

  /**
   * The image component for the half heart.
   */
  BufferedImage halfHeart;

  /**
   * The image component for the empty heart.
   */
  BufferedImage emptyHeart;

  /**
   * A boolean flag indicating whether a message should be displayed.
   */
  private boolean displayMessage = false;

  /**
   * The message to display.
   */
  private String message = "";

  /**
   * The frame counter for the message display.
   */
  private int messageFrameCounter;


  /**
   * Constructor for the ui class object that initializes all variables.
   * This object is used to draw over the game and display important stats.
   *
   * @param gp the panel attached to game window in which stats are drawn.
   */
  public UserInterface(GamePanel gp) {
    UserInterface.gp = gp;
    UserInterface.font = new Font("Arial", Font.PLAIN, 40);
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
   *
   * @param text specialized text for each action giving feedback/updating player on info.
   */
  public void showMessage(String text) {
    message = text;
    displayMessage = true;
  }

  /**
   * method to draw stats for the game.
   *
   * @param g2 the panel attached to game window.
   */
  public void draw(Graphics2D g2) {
    g2.setFont(font);
    g2.setColor(Color.white);
    g2.drawImage(keyImage, TILE_SIZE / 2, TILE_SIZE / 2, TILE_SIZE, TILE_SIZE, null);
    g2.drawString("x " + gp.player.getCurrentRubies(), 74, 65);
    drawPlayerLife(g2);

    // popping message on window
    if (displayMessage) {
      g2.setFont(g2.getFont().deriveFont(30F));
      g2.drawString(message, TILE_SIZE / 2, TILE_SIZE * 5);
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
   * used to draw the heart images, showing the Status of current amount of lives.
   */
  public void drawPlayerLife(Graphics2D g2) {
    int x = TILE_SIZE * 10; // x coordinate of the lives display on screen
    int y = TILE_SIZE / 3; // y coordinate of the lives display on screen
    int i = 0; // counts the number of heart displayed already on screen respect to max lives

    // DRAW MAX LIFE
    // display half the max lives because each heart is 2 sub-lives
    while (i < gp.player.getLives() / 2) {
      g2.drawImage(emptyHeart, x, y, TILE_SIZE, TILE_SIZE, null);
      i++;
      x += TILE_SIZE;
    }

    // RESET after drawing emptyHearts (displaying full life but BLANK)
    x = TILE_SIZE * 10;
    i = 0;

    // DRAW CURRENT LIFE
    while (i < gp.player.getLives()) {
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
