package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Entities.Villager;
import org.project.ui.GamePanel;

import static org.project.SystemVariables.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class VillagerTest {

  private Villager testVillager;
  
  @BeforeEach
  public void setUp() {
    GamePanel testGamePanel = GamePanel.getGamePanel();
    testVillager = new Villager(testGamePanel,10,10);
  }

  @Test
  public void testSetAction() {
    // Check direction and speed. Similar testing from Entity.
    assertEquals(Directions.DOWN, testVillager.peekDirection());
    assertEquals(2, testVillager.getSpeed());

    // Invoke setAction a bunch of  times to ensure direction change randomly.
    Directions initialDirection = testVillager.peekDirection();
    for (int i = 0; i < 10; i++) {
      testVillager.setAction();
      if (testVillager.peekDirection() != initialDirection) {
        break;
      }
    }
    assertNotEquals(initialDirection, testVillager.peekDirection());
  }

  @Test
  public void testUpdate() {
    int initialWorldX = testVillager.getWorldX();
    int initialWorldY = testVillager.getWorldY();

    // Update and check worldX and worldY for change in line with speed and direction
    testVillager.update();
    assertEquals(initialWorldX, testVillager.getWorldX());
    assertEquals(initialWorldY + testVillager.getSpeed(), testVillager.getWorldY());

    // Change direction to test again ...
    testVillager.changeDirection(Directions.LEFT);
    testVillager.update();
    assertEquals(initialWorldX - testVillager.getSpeed(), testVillager.getWorldX());
    assertEquals(initialWorldY + testVillager.getSpeed(), testVillager.getWorldY());
  }

  @Test
  public void testSolidArea() {
    // Check the box !
    Rectangle solidArea = testVillager.hitbox;
    assertEquals(8, solidArea.x);
    assertEquals(8, solidArea.y);
    assertEquals(32, solidArea.width);
    assertEquals(32, solidArea.height);
  }
}
