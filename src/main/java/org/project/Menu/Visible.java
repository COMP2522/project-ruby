package org.project.Menu;

/**
 * Visible interface represents an object that can be set visible or invisible.
 *
 * @author Greg Song
 * @version 2023-04-09
 */
public interface Visible {
  /**
   * Sets the visibility status of the object.
   *
   * @param visible true if object should be visible, false otherwise.
   */
  void setVisible(boolean visible);

  /**
   * Returns the visibility status of the object.
   *
   * @return true if object is visible, false otherwise.
   */
  boolean isVisible();
}
