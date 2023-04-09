package org.project;

import java.awt.*;

public class Button extends Component {
  public Button(int x, int y, int width, int height) {
    super(x, y, width, height);
  }

  public void draw(Graphics2D g) {
    g.fillRect(0, 0, getWidth(), getHeight());
  }
}
