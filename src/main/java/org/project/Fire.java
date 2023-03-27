package org.project;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class Fire extends Object{
  public Fire() {
    name = "Fire";
    try {
      image = ImageIO.read(new FileInputStream("assets/mapData/objects/fire1.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    collision = true;
  }
}
