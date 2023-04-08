package org.project.Objects;

import org.project.Objects.Element;

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

  /** Constructs a Power-up object and sets its image, and collision properties. */
  public PowerUp() {
    try {
      setImage(ImageIO.read(new FileInputStream("assets/mapData/objects/powerup2.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    setCollision(true);
  }
}