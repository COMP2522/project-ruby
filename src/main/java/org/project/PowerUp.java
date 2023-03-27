package org.project;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class PowerUp extends Object{
  public PowerUp() {
    name = "Fast";
    try {
      image = ImageIO.read(new FileInputStream("assets/mapData/objects/powerup2.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    collision = true;
  }
}
