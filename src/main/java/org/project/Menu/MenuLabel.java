package org.project.Menu;

import java.awt.*;

/**
 * Represents a label component in the Menu. It extends MenuTextBox and
 * implements the Visible interface, allowing it to be hidden.
 *
 * @author Greg Song
 * @version 2023-04-09
 */
public class MenuLabel extends MenuTextBox implements Visible {
  private boolean isVisible;

  /**
   * Constructs a MenuLabel.
   * @param x         The x-coordinate of the label.
   * @param y         The y-coordinate of the label.
   * @param width     The width of the label.
   * @param height    The height of the label.
   * @param bgColor   The background color of the label.
   * @param textColor The text color of the label.
   * @param font      The font of the label.
   * @param text      The text to display in the label.
   * @param isVisible Whether the label is currently visible or not.
   */
  public MenuLabel(int x, int y, int width, int height, Color bgColor,
                   Color textColor, Font font, String text, Boolean isVisible) {
    super(x, y, width, height, bgColor, textColor, font, text);
    this.isVisible = isVisible;
  }

  /**
   * Overrides draw method, used to draw the label.
   *
   * @param g a Graphics object.
   */
  @Override
  public void draw(Graphics g) {
    if (isVisible) {
      super.draw(g);
    }
  }

  /**
   * Sets the visibility Status of the label.
   *
   * @param visible true if object should be visible, false otherwise.
   */
  @Override
  public void setVisible(boolean visible) {
    this.isVisible = visible;
  }

  /**
   * Gets the visibility Status of the label.
   *
   * @return True if object is currently visible, false otherwise.
   */
  @Override
  public boolean isVisible() {
    return this.isVisible;
  }
}
