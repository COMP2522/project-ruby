package org.project;

import org.project.Entities.Monster;
import org.project.Entities.Villager;

import static org.project.SystemVariables.*;

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
  private final GamePanel gp;

  /**
   * Constructs an ElementHandler object with the specified GamePanel.
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
    gp.elements[0].setWorldX(17 * TILE_SIZE);
    gp.elements[0].setWorldY(38 * TILE_SIZE);

    gp.elements[1] = new Ruby();
    gp.elements[1].setWorldX(32 * TILE_SIZE);
    gp.elements[1].setWorldY(40 * TILE_SIZE);

    gp.elements[2] = new Door();
    gp.elements[2].setWorldX(10 * TILE_SIZE);
    gp.elements[2].setWorldY(11 * TILE_SIZE);

    gp.elements[3] = new Ruby();
    gp.elements[3].setWorldX(12 * TILE_SIZE);
    gp.elements[3].setWorldY(42 * TILE_SIZE);

    gp.elements[4] = new PowerUp();
    gp.elements[4].setWorldX(23 * TILE_SIZE);
    gp.elements[4].setWorldY(7 * TILE_SIZE);

    gp.elements[5] = new Ruby();
    gp.elements[5].setWorldX(36 * TILE_SIZE);
    gp.elements[5].setWorldY(31 * TILE_SIZE);

    gp.elements[6] = new Ruby();
    gp.elements[6].setWorldX(38 * TILE_SIZE);
    gp.elements[6].setWorldY(41 * TILE_SIZE);

    gp.elements[7] = new Fire();
    gp.elements[7].setWorldX(19 * TILE_SIZE);
    gp.elements[7].setWorldY(37 * TILE_SIZE);

    gp.elements[8] = new Fire();
    gp.elements[8].setWorldX(20 * TILE_SIZE);
    gp.elements[8].setWorldY(36 * TILE_SIZE);

    gp.elements[9] = new Fire();
    gp.elements[9].setWorldX(21 * TILE_SIZE);
    gp.elements[9].setWorldY(39 * TILE_SIZE);

    gp.elements[10] = new Fire();
    gp.elements[10].setWorldX(22 * TILE_SIZE);
    gp.elements[10].setWorldY(40 * TILE_SIZE);


  }

  /**
   * Sets the positions of the non-player characters (NPCs) within the game panel.
   */
  public void setNPC() {
    gp.npc[0] = new Villager(gp,24,10);
    gp.npc[0].setWorldX((gp.npc[0]).getWorldX() * TILE_SIZE);
    gp.npc[0].setWorldY((gp.npc[0]).getWorldY() * TILE_SIZE);

  }

  /**
   * Sets the positions of the monsters within the game panel.
   */
  public void setMonster() {
    gp.monster[0] = new Monster(gp, 24,15);
    gp.monster[0].setWorldX((gp.monster[0]).getWorldX() * TILE_SIZE);
    gp.monster[0].setWorldY((gp.monster[0]).getWorldY() * TILE_SIZE);

    gp.monster[1] = new Monster(gp, 13, 9);
    gp.monster[1].setWorldX((gp.monster[1]).getWorldX() * TILE_SIZE);
    gp.monster[1].setWorldY((gp.monster[1]).getWorldY() * TILE_SIZE);

    gp.monster[2] = new Monster(gp, 24, 40);
    gp.monster[2].setWorldX((gp.monster[2]).getWorldX() * TILE_SIZE);
    gp.monster[2].setWorldY((gp.monster[2]).getWorldY() * TILE_SIZE);

    gp.monster[3] = new Monster(gp, 30, 38);
    gp.monster[3].setWorldX((gp.monster[3]).getWorldX() * TILE_SIZE);
    gp.monster[3].setWorldY((gp.monster[3]).getWorldY() * TILE_SIZE);

  }
}
