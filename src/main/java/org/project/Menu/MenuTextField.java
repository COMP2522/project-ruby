package org.project.Menu;

import java.awt.*;

/**
 * Represents a text field component in a Menu. It extends the MenuComponent
 * class and implements Visible, allowing it to be hidden.
 * @author Greg Song
 * @version 2023-04-09
 */
public class MenuTextField extends MenuComponent implements Visible {
  private static final int H_PADDING = 5;
  private static final int V_PADDING = 20;
  private String input;
  private final int maxLength;
  private final Font font;
  private final Color bgColor = Color.white;
  private Color textColor = Color.black;
  private boolean isVisible;

  /**
   * Constructs a MenuTextField.
   * @param x The x-coordinate of the text field.
   * @param y The y-coordinate of the text field.
   * @param width The width of the text field.
   * @param height The height of the text field.
   * @param font The font of the text in the text field.
   * @param maxLength The maximum length of the input in the text field.
   * @param isVisible Whether the text field is visible or not.
   */
  public MenuTextField(int x, int y, int width, int height, Font font, int maxLength, boolean isVisible) {
    super(x, y, width, height);
    this.font = font;
    this.maxLength = maxLength;
    this.input = "";
    this.isVisible = isVisible;
  }

  /**
   * Overrides the draw method. Used to draw this text field.
   * @param g The Graphics object used to draw.
   */
  @Override
  public void draw(Graphics g) {
    if (isVisible) {
      g.setColor(bgColor);
      g.fillRect(x, y, width, height);
      g.setColor(textColor);
      g.drawRect(x, y, width, height);
      g.setFont(font);
      g.drawString(input, x + H_PADDING, y + V_PADDING);
    }
  }

  /**
   * Adds a character to the input of the text field.
   * @param c The character to be added.
   */
  public void addChar(char c) {
    if (input.length() < maxLength) {
      input += c;
    }
  }

  /**
   * Removes the last character of the input.
   */
  public void removeChar() {
    if (input.length() > 0) {
      input = input.substring(0, input.length() - 1);
    }
  }

  /**
   * Gets the current input of the text field.
   * @return The current input, a String
   */
  public String getInput() {
    return input;
  }

  /**
   * Sets the visibility status of the text field.
   * @param visible true if object should be visible, false otherwise.
   */
  public void setVisible(boolean visible) {
    this.isVisible = visible;
  }

  /**
   * Returns the visibility status of the text field.
   * @return True if currently visible, false otherwise.
   */
  public boolean isVisible() {
    return this.isVisible;
  }

  public void setTextColor(Color textColor) {
    this.textColor = textColor;
  }
}
