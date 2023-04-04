package org.project;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Defines the heart element that is displayed on top of the overlay UI.
 *
 * @author Abhishek Chouhan
 * @version 2023-03-31
 */
public class Life extends Element {

  // defining two extra buffered images to display a half and empty life on UI
  // one buffered image is already being inherited from the superclass
  public BufferedImage halfLife;
  public BufferedImage emptyLife;

  /**
   * Constructs a heart object and sets its name, image, and collision properties.
   */
  public Life() {
    try {
      setImage(ImageIO.read(new FileInputStream("assets/player/fullHeart.png")));
      halfLife = ImageIO.read(new FileInputStream("assets/player/halfHeart.png"));
      emptyLife = ImageIO.read(new FileInputStream("assets/player/emptyHeart.png"));

    } catch (IOException e) {
      e.printStackTrace();
    }
    setCollision(true);
  }
}
