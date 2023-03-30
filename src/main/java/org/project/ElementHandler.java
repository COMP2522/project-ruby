package org.project;


/**
 * The ElementHandler class is responsible for handling the placement
 * of elements in the map, including non-player characters (NPCs),
 * monsters, and intractable objects such as doors, rubies, and
 * power-ups. The class contains methods to set the positions of these
 * elements within the game panel.
 *
 * @author Nathan Bartyuk, Simrat Kaur, Abhishek Chouhan, Amrit Jhatu, Greg
 * @version 2023-02-07
 */
public class ElementHandler {
  GamePanel gp;

  /**
   * Constructs an ElementHandler object with the specified GamePanel.
   *
   * @param gp game panel in which the elements are placed
   */
  public ElementHandler(GamePanel gp) {
    this.gp = gp;
  }

  /**
   * Sets the positions of the intractable objects within the game panel.
   */
  public void setElement() {
    gp.elements[0] = new Ruby();
    gp.elements[0].setWorldX(17 * GamePanel.TILE_SIZE);
    gp.elements[0].setWorldY(38 * GamePanel.TILE_SIZE);

    gp.elements[1] = new Ruby();
    gp.elements[1].setWorldX(32 * GamePanel.TILE_SIZE);
    gp.elements[1].setWorldY(40 * GamePanel.TILE_SIZE);

    gp.elements[2] = new Door();
    gp.elements[2].setWorldX(10 * GamePanel.TILE_SIZE);
    gp.elements[2].setWorldY(11 * GamePanel.TILE_SIZE);

    gp.elements[3] = new Ruby();
    gp.elements[3].setWorldX(12 * GamePanel.TILE_SIZE);
    gp.elements[3].setWorldY(42 * GamePanel.TILE_SIZE);

    gp.elements[4] = new PowerUp();
    gp.elements[4].setWorldX(23 * GamePanel.TILE_SIZE);
    gp.elements[4].setWorldY(7 * GamePanel.TILE_SIZE);

    gp.elements[5] = new Ruby();
    gp.elements[5].setWorldX(36 * GamePanel.TILE_SIZE);
    gp.elements[5].setWorldY(31 * GamePanel.TILE_SIZE);

    gp.elements[6] = new Ruby();
    gp.elements[6].setWorldX(38 * GamePanel.TILE_SIZE);
    gp.elements[6].setWorldY(41 * GamePanel.TILE_SIZE);

    gp.elements[7] = new Fire();
    gp.elements[7].setWorldX(19 * GamePanel.TILE_SIZE);
    gp.elements[7].setWorldY(37 * GamePanel.TILE_SIZE);

    gp.elements[8] = new Fire();
    gp.elements[8].setWorldX(19 * GamePanel.TILE_SIZE);
    gp.elements[8].setWorldY(36 * GamePanel.TILE_SIZE);

    gp.elements[9] = new Fire();
    gp.elements[9].setWorldX(19 * GamePanel.TILE_SIZE);
    gp.elements[9].setWorldY(39 * GamePanel.TILE_SIZE);

    gp.elements[10] = new Fire();
    gp.elements[10].setWorldX(19 * GamePanel.TILE_SIZE);
    gp.elements[10].setWorldY(40 * GamePanel.TILE_SIZE);


  }

  /**
   * Sets the positions of the non-player characters (NPCs) within the game panel.
   */
  public void setNPC() {
    gp.npc[0] = new Villager(gp);
    gp.npc[0].worldX = 24 * GamePanel.TILE_SIZE;
    gp.npc[0].worldY = 15 * GamePanel.TILE_SIZE;

  }

  /**
   * Sets the positions of the monsters within the game panel.
   */
  public void setMonster() {
    gp.monster[0] = new Monster(gp);
    gp.monster[0].worldX = 24* GamePanel.TILE_SIZE;
    gp.monster[0].worldY = 15* GamePanel.TILE_SIZE;

    gp.monster[0] = new Monster(gp);
    gp.monster[0].worldX = 24* GamePanel.TILE_SIZE;
    gp.monster[0].worldY = 15* GamePanel.TILE_SIZE;

  }
}
