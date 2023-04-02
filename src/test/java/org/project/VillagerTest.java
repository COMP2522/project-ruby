package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Entity.directions;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class VillagerTest {

  private Villager testVillager;
  private GamePanel testGamePanel;

  @BeforeEach
  public void setUp() {
    testGamePanel = new GamePanel();
    testVillager = new Villager(testGamePanel);
  }

  @Test
  public void testSetAction() {
    // Check direction and speed. Similar testing from Entity.
    assertEquals(directions.DOWN, testVillager.direction);
    assertEquals(2, testVillager.speed);

    // Invoke setAction a bunch of  times to ensure direction change randomly.
    directions initialDirection = testVillager.direction;
    for (int i = 0; i < 10; i++) {
      testVillager.setAction();
      if (testVillager.direction != initialDirection) {
        break;
      }
    }
    assertNotEquals(initialDirection, testVillager.direction);
  }

  @Test
  public void testUpdate() {
    int initialWorldX = testVillager.getWorldX();
    int initialWorldY = testVillager.getWorldY();

    // Update and check worldX and worldY for change in line with speed and direction
    testVillager.update();
    assertEquals(initialWorldX, testVillager.getWorldX());
    assertEquals(initialWorldY + testVillager.speed, testVillager.getWorldY());

    // Change direction to test again ...
    testVillager.direction = directions.LEFT;
    testVillager.update();
    assertEquals(initialWorldX - testVillager.speed, testVillager.getWorldX());
    assertEquals(initialWorldY + testVillager.speed, testVillager.getWorldY());
  }

  @Test
  public void testSolidArea() {
    // Check the box !
    Rectangle solidArea = testVillager.solidArea;
    assertEquals(8, solidArea.x);
    assertEquals(8, solidArea.y);
    assertEquals(32, solidArea.width);
    assertEquals(32, solidArea.height);
  }
}
