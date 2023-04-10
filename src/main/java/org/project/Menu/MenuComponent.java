package org.project.Menu;

import java.awt.*;

/**
 * Abstract class for components for Menu. It provides the most common properties
 * of menu components. All other components found in Menu extend from this.
 *
 * @author Greg Song
 * @version 2023-04-09
 */
public abstract class MenuComponent {
  protected int x;
  protected int y;
  protected int width;
  protected int height;

  /**
   * Constructs a new MenuO
   *
   * @param x      The x-coordinate of the MenuComponent.
   * @param y      The y-coordinate of the MenuComponent.
   * @param width  The width of the MenuComponent.
   * @param height The height of the MenuComponent.
   */
  public MenuComponent(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
   * Abstract method to draw MenuComponents using Graphics object.
   *
   * @param g The Graphics object used to draw.
   */
  public abstract void draw(Graphics g);

  /**
   * Gets the width of the MenuComponent.
   *
   * @return The width
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Gets the height of the MenuComponent.
   *
   * @return The height.
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Gets the x-cooridnate of the MenuComponent.
   *
   * @return The x-coordinate.
   */
  public int getX() {
    return this.x;
  }

  /**
   * Gets the y-coordinate of the MenuComponent.
   *
   * @return The y-coordinate.
   */
  public int getY() {
    return this.y;
  }
}
