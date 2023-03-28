package org.project;

public class ObjectHandler {
  GamePanel gp;

  public ObjectHandler(GamePanel gp) {
    this.gp = gp;
  }

  public void setObject() {
    gp.objects[0] = new Ruby();
    gp.objects[0].worldX = 9 * GamePanel.TILE_SIZE;
    gp.objects[0].worldY = 7 * GamePanel.TILE_SIZE;

    gp.objects[1] = new Ruby();
    gp.objects[1].worldX = 11 * GamePanel.TILE_SIZE;
    gp.objects[1].worldY = 7 * GamePanel.TILE_SIZE;

    gp.objects[2] = new Door();
    gp.objects[2].worldX = 10 * GamePanel.TILE_SIZE;
    gp.objects[2].worldY = 11 * GamePanel.TILE_SIZE;

    gp.objects[3] = new Fire();
    gp.objects[3].worldX = 10 * GamePanel.TILE_SIZE;
    gp.objects[3].worldY = 31 * GamePanel.TILE_SIZE;

    gp.objects[4] = new PowerUp();
    gp.objects[4].worldX = 10 * GamePanel.TILE_SIZE;
    gp.objects[4].worldY = 14 * GamePanel.TILE_SIZE;
  }

  public void setNPC() {
    gp.npc[0] = new NPC_Villager(gp);
    gp.npc[0].worldX = 10* GamePanel.TILE_SIZE;
    gp.npc[0].worldY = 10* GamePanel.TILE_SIZE;

  }

  public void setMonster(){
    gp.monster[0] = new MON_Beast(gp);
    gp.monster[0].worldX = 10* GamePanel.TILE_SIZE;
    gp.monster[0].worldY = 10* GamePanel.TILE_SIZE;

    gp.monster[0] = new MON_Beast(gp);
    gp.monster[0].worldX = 10* GamePanel.TILE_SIZE;
    gp.monster[0].worldY = 10* GamePanel.TILE_SIZE;

  }
}
