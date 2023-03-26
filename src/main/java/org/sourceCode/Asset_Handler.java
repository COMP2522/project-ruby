package org.sourceCode;

public class Asset_Handler {
  GamePanel gp;

  public Asset_Handler (GamePanel gp) {
    this.gp = gp;
  }

  public void setObject() {
    gp.objects[0] = new Ruby2();
    gp.objects[0].x = 5 * gp.tileSize;
    gp.objects[0].y = 11 * gp.tileSize;

    gp.objects[1] = new Ruby2();
    gp.objects[1].x = 6 * gp.tileSize;
    gp.objects[1].y = 11 * gp.tileSize;

    gp.objects[2] = new Door();
    gp.objects[2].x = 6 * gp.tileSize;
    gp.objects[2].y = 6 * gp.tileSize;

  }
}
