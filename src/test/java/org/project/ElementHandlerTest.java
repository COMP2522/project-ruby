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
}
