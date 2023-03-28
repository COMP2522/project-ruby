package org.project;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class Ruby extends Element {
  public Ruby() {
    name = "Ruby";
    try {
      image = ImageIO.read(new FileInputStream("assets/mapData/objects/ruby.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    collision = true;
  }
}
