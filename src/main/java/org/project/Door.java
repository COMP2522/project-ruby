package org.project;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class Door extends Element {
  public Door() {
    name = "Door";
    try {
      image = ImageIO.read(new FileInputStream("assets/mapData/objects/door2.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    collision = true;
  }
}
