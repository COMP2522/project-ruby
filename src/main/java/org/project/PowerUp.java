package org.project;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Defines the Power up element that is to be collected by the player and
 * makes the player faster.
 *
 * @author Nathan Bartyuk, Simrat Kaur, Abhishek Chouhan, Amrit Jhatu, Greg
 * @version 2023-02-07
 */
public class PowerUp extends Element {

  /**
   Constructs a Power-up object and sets its name, image, and collision properties.   * Loads the power-up image from file and sets its collision to true.
   */
  public PowerUp() {
    setName("PowerUp");
    try {
      setImage(ImageIO.read(new FileInputStream("assets/mapData/objects/powerup2.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    setCollision(true);
  }
}
