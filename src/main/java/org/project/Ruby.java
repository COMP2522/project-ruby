package org.project;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Defines the ruby element that is to be collected by the player throughout the game.
 *
 * @author Nathan Bartyuk, Simrat Kaur, Abhishek Chouhan, Amrit Jhatu, Greg
 * @version 2023-02-07
 */
public class Ruby extends Element {

  /** Constructs a Ruby object and sets its image, and collision properties. */
  public Ruby() {
    try {
      setImage(ImageIO.read(new FileInputStream("assets/mapData/objects/ruby.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    setCollision(true);
  }
}
