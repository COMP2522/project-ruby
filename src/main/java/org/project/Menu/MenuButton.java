package org.project.Menu;

import java.awt.*;

/**
 * MenuButton class represents a button component for a menu. It extends
 * MenuTextBox.
 *
 * @author Greg Song
 * @version 2023-04-09
 */
public class MenuButton extends MenuTextBox {
  /**
   * ButtonName used to differentiate which button is active.
   */
  public enum ButtonName {
    NEW_GAME,
    LOAD_GAME,
    BACK,
    SUBMIT
  }

  private ButtonName buttonName;

  /**
   * Constructs a new MenuButton.
   *
   * @param x          The x-coordinate of top-left corner of the text box.
   * @param y          The y-coordinate of the top-left corner of the text box.
   * @param width      Width of the text box.
   * @param height     Height of the text box.
   * @param bgColor    Background color
   * @param textColor  Text color
   * @param font       Font of the text in the text box.
   * @param text       String of the text to display.
   * @param buttonName the Type of the button.
   */
  public MenuButton(int x, int y, int width, int height, Color bgColor,
                    Color textColor, Font font, String text,
                    ButtonName buttonName) {
    super(x, y, width, height, bgColor, textColor, font, text);
    this.buttonName = buttonName;
  }

  /**
   * Draws the button using Graphics.
   *
   * @param g a Graphics object.
   */
  public void draw(Graphics g) {
    super.draw(g);
  }

  /**
   * Checks if the given x and y are within the bounds of the button. Used to
   * check if button is clicked.
   *
   * @param x The x-coordinate of the point.
   * @param y The y-coordinate of the point.
   * @return true if given x and y are within bound of this button.
   */
  public boolean contains(int x, int y) {
    return x >= getX() && x <= getX() + getWidth()
        && y >= getY() && y <= getY() + getHeight();
  }

  /**
   * Sets the buttonName and text.
   *
   * @param buttonName Name of the button.
   * @param text       The text to display on the button.
   */
  public void setButton(ButtonName buttonName, String text) {
    this.buttonName = buttonName;
    this.text = text;
  }

  /**
   * Returns the current buttonName.
   *
   * @return The name of the Button.
   */
  public ButtonName getButtonName() {
    return this.buttonName;
  }
}
