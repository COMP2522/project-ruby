package org.project;

public class AssetHandler {
  GamePanel gp;

  public AssetHandler(GamePanel gp) {
    this.gp = gp;
  }

  public void setObject() {
    gp.objects[0] = new Ruby();
    gp.objects[0].x = 5 * GamePanel.TILE_SIZE;
    gp.objects[0].y = 11 * GamePanel.TILE_SIZE;

    gp.objects[1] = new Ruby();
    gp.objects[1].x = 6 * GamePanel.TILE_SIZE;
    gp.objects[1].y = 11 * GamePanel.TILE_SIZE;

    gp.objects[2] = new Door();
    gp.objects[2].x = 6 * GamePanel.TILE_SIZE;
    gp.objects[2].y = 6 * GamePanel.TILE_SIZE;

    gp.objects[3] = new Fire();
    gp.objects[3].x = 5 * GamePanel.TILE_SIZE;
    gp.objects[3].y = 7 * GamePanel.TILE_SIZE;

    gp.objects[4] = new PowerUp();
    gp.objects[4].x = 4 * GamePanel.TILE_SIZE;
    gp.objects[4].y = 4 * GamePanel.TILE_SIZE;
  }
}
