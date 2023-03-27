package org.sourceCode;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class Ruby2 extends Object{
  public Ruby2() {
    name = "Ruby";
    try {
//      image = ImageIO.read(getClass().getResourceAsStream("assets/mapData/images/fire1.png"));
      image = ImageIO.read(new FileInputStream("assets/mapData/images/ruby.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    collision = true;
  }
}
