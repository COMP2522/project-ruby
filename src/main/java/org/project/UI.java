package org.project;

import java.awt.*;

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

  /**
   * Constructor for the UI class object that initializes all variables.
   * This object is used to draw over the game and display important stats.
   * @param gp the panel attached to game window in which stats are drawn.
   */
  public UI(GamePanel gp) {
    this.gp = gp;
    // initialize Window's font to Arial type and 40 size
    this.arial_40 = new Font("Arial", Font.PLAIN, 40);
  }

  /**
   * method to draw stats for the game.
   * @param g2 the panel attached to game window.
   */
  public void draw(Graphics2D g2) {
    g2.setFont(arial_40);
    g2.setColor(Color.white);
    // 50, 50 are the (x, y) coordinates of the box displaying number of rubies
    g2.drawString("Rubies = " + gp.player.getCurrentRubies(), 50, 50);

  }
}
