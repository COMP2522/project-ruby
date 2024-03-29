package org.project.Objects;

import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Defines the ruby element that is to be collected by the player throughout the game.
 *
 * @author Nathan Bartyuk, Simrat Kaur, Abhishek Chouhan, Amrit Jhatu, Greg
 * @version 2023-02-07
 */
public class Ruby extends Element {

  /**
   * Constructs a Ruby object and sets its image, and collision properties.
   */
  public Ruby() {
    try {
      setImage(ImageIO.read(new FileInputStream("assets/data/objects/ruby.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    setCollision(true);
  }
}
