package org.project.Objects;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The Door class represents a door element in the game that can only be opened
 * when the player has collected all the rubies.
 *
 * @author Simrat Kaur
 * @version 2023-02-07
 */
public class Door extends Element {

  /** Constructs a Door object and sets its name, image, and collision properties. */
  public Door() {
    try {
      setImage(ImageIO.read(new FileInputStream("assets/mapData/objects/door1.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    setCollision(true);
  }
}
