package org.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElementHandlerTest {
  private GamePanel gp;
  private ElementHandler elementHandler;

  @BeforeEach
  public void setup() {
    gp = new GamePanel();
    elementHandler = new ElementHandler(gp);
  }

  @Test
  public void testSetElement() {
    elementHandler.setElement();
    Assertions.assertTrue(gp.elements[0] instanceof Ruby);
    Assertions.assertEquals(17 * GamePanel.TILE_SIZE, gp.elements[0].getWorldX());
    Assertions.assertEquals(38 * GamePanel.TILE_SIZE, gp.elements[0].getWorldY());
  }

  @Test
  public void testSetNPC() {
    elementHandler.setNPC();
    Assertions.assertTrue(gp.npc[0] instanceof Villager);
    Assertions.assertEquals(24 * GamePanel.TILE_SIZE, gp.npc[0].getWorldX());
    Assertions.assertEquals(10 * GamePanel.TILE_SIZE, gp.npc[0].getWorldY());
  }

  @Test
  public void testSetMonster() {
    elementHandler.setMonster();
    Assertions.assertTrue(gp.monster[0] instanceof Monster);
    Assertions.assertEquals(24 * GamePanel.TILE_SIZE, gp.monster[0].getWorldX());
    Assertions.assertEquals(15 * GamePanel.TILE_SIZE, gp.monster[0].getWorldY());
  }
  // Add more tests here for the BOSS if required.

}
