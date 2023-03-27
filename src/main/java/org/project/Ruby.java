package org.project;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class Ruby extends Object{
  public Ruby() {
    name = "Ruby";
    try {
      image = ImageIO.read(new FileInputStream("assets/mapData/images/ruby.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    collision = true;
  }
}
