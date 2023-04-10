package org.project.Menu;

import java.awt.*;

/**
 * MenuTextBox is an abstract class that represents a Component that is
 * colored rectangular box that can hold text to display. It extends MenuComponent.
 *
 * @author Greg Song
 * @version 2023-04-09
 */
public abstract class MenuTextBox extends MenuComponent {
  protected Color bgColor;
  protected Color textColor;
  protected Font font;
  protected String text;

  /**
   * Constructs a new instance of MenuTextBox.
   *
   * @param x The x-coordinate of top-left corner of the text box.
   * @param y The y-coordinate of the top-left corner of the text box.
   * @param width Width of the text box.
   * @param height Height of the text box.
   * @param bgColor Background color
   * @param textColor Text color
   * @param font Font of the text in the text box.
   * @param text String of the text to display.
   */
  public MenuTextBox(int x, int y, int width, int height, Color bgColor,
                     Color textColor, Font font, String text) {
    super(x, y, width, height);
    this.bgColor = bgColor;
    this.textColor = textColor;
    this.font = font;
    this.text = text;
  }

  /**
   * Used to draw the MenuTextBox.
   *
   * @param g a Graphics object.
   */
  @Override
  public void draw(Graphics g) {
    g.setColor(bgColor);
    g.fillRect(x, y, width, height);

    g.setFont(font);
    FontMetrics fontMetrics = g.getFontMetrics();
    int textWidth = fontMetrics.stringWidth(text);
    int textHeight = fontMetrics.getHeight();
    int textX = x + (width - textWidth) / 2;
    int textY = y + (height + textHeight) / 2;
    g.setColor(textColor);

    g.drawString(text, textX, textY);
  }
}
