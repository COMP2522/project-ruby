package org.project.Menu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Represents an image component in the Menu. It extends the MenuComponent class,
 * loads and displays an image.
 *
 * @author Greg Song
 * @version 2023-04-09
 */
public class MenuImage extends MenuComponent {
  private Image image;

  /**
   * Constructs a MenuImage.
   *
   * @param x         The x-coordinate of this MenuImage.
   * @param y         The y-coordinate of this MenuImage.
   * @param width     The width of this MenuImage.
   * @param height    The height of this MenuImage.
   * @param imagePath The file path of the image to display.
   * @throws IOException If there is an error loading the image at given file path.
   */
  public MenuImage(int x, int y, int width, int height, String imagePath) throws IOException {
    super(x, y, width, height);
    loadImage(imagePath);
  }

  /**
   * Loads the image from given file path.
   *
   * @param imagePath The file path of the image file.
   * @throws IOException If there is an error loading the image at given file path.
   */
  private void loadImage(String imagePath) throws IOException {
    try {
      image = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      throw new IOException(e);
    }
  }

  /**
   * Overrides draw method. Used to draw this image.
   *
   * @param g The Graphics object used to draw.
   */
  @Override
  public void draw(Graphics g) {
    g.drawImage(image, x, y, width, height, null);
  }
}
