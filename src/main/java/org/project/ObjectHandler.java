package org.project;

public class ObjectHandler {
  GamePanel gp;

  public ObjectHandler(GamePanel gp) {
    this.gp = gp;
  }

  public void setObject() {
    gp.elements[0] = new Ruby();
    gp.elements[0].worldX = 9 * GamePanel.TILE_SIZE;
    gp.elements[0].worldY = 7 * GamePanel.TILE_SIZE;

    gp.elements[1] = new Ruby();
    gp.elements[1].worldX = 11 * GamePanel.TILE_SIZE;
    gp.elements[1].worldY = 7 * GamePanel.TILE_SIZE;

    gp.elements[2] = new Door();
    gp.elements[2].worldX = 10 * GamePanel.TILE_SIZE;
    gp.elements[2].worldY = 11 * GamePanel.TILE_SIZE;

    gp.elements[3] = new Fire();
    gp.elements[3].worldX = 10 * GamePanel.TILE_SIZE;
    gp.elements[3].worldY = 31 * GamePanel.TILE_SIZE;

    gp.elements[4] = new PowerUp();
    gp.elements[4].worldX = 10 * GamePanel.TILE_SIZE;
    gp.elements[4].worldY = 14 * GamePanel.TILE_SIZE;
  }

  public void setNPC() {
    gp.npc[0] = new NPC_Villager(gp);
    gp.npc[0].worldX = 10* GamePanel.TILE_SIZE;
    gp.npc[0].worldY = 10* GamePanel.TILE_SIZE;

  }
}
