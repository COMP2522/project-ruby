package org.project.UI;

import java.awt.*;
import javax.swing.*;

/**
 * ImagePanel a helper class for MenuHandler. Creates a JPanel with set background image.
 */
public class ImagePanel extends JPanel {
  private static final String backgroundImage = "assets/menu/jungle.jpeg";

  /**
   * Add backgroundImage to this JPanel.
   *
   * @param g the <code>Graphics</code> object to protect
   */
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(new ImageIcon(backgroundImage).getImage(), 0, 0, getWidth(), getHeight(), this);
  }

}
