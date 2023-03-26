package org.sourceCode;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class Door extends Object{

  public Door() {
    name = "Door";
    try {
//      image = ImageIO.read(getClass().getResourceAsStream("assets/mapData/images/fire1.png"));
      image = ImageIO.read(new FileInputStream("assets/mapData/images/door2.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    collision = true;
  }
}
