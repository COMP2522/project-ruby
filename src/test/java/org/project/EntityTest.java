package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.project.Entity.directions.*;

public class EntityTest {

  private TestEntity testEntity;
  private GamePanel testGamePanel;

  @BeforeEach
  public void setUp() {
    testGamePanel = new GamePanel();
    testEntity = new TestEntity(testGamePanel);
  }

  @Test
  public void testSetAction() {
    // Check the initial direction and speed below.
    assertEquals(LEFT, testEntity.direction);
    assertEquals(2, testEntity.speed);

    // Another change to direction and speed, and test again.
    testEntity.direction = UP;
    testEntity.speed = 3;
    testEntity.setAction();
    assertEquals(UP, testEntity.direction);
    assertEquals(3, testEntity.speed);
  }

  @Test
  public void testUpdate() {
    // Store initial  value of ... worldX and worldY
    int initialWorldX = testEntity.getWorldX();
    int initialWorldY = testEntity.getWorldY();

    // Update to check if worldX and worldY have changed with speed and direction in mind.
    testEntity.update();
    assertEquals(initialWorldX - testEntity.speed, testEntity.getWorldX());
    assertEquals(initialWorldY, testEntity.getWorldY());

    // Another test for a differing direction.
    testEntity.direction = RIGHT;
    testEntity.update();
    assertEquals(initialWorldX, testEntity.getWorldX());
    assertEquals(initialWorldY, testEntity.getWorldY());
  }

  @Test
  public void testWorldXWorldYGetterSetter() {
    // Set another grouping worldX and worldY values
    testEntity.setWorldX(100);
    testEntity.setWorldY(200);

    // Check if the new values are set correctly
    assertEquals(100, testEntity.getWorldX());
    assertEquals(200, testEntity.getWorldY());
  }

  private static class TestEntity extends Entity {

    public TestEntity(GamePanel gp) {
      super(gp);
      direction = LEFT;
      speed = 2;
    }

    @Override
    public void setAction() {
      // Sample for setAction() method.
      direction = UP;
      speed = 3;
    }

    @Override
    public BufferedImage setup(String imagePath) {
      return null;
    }
  }
}
