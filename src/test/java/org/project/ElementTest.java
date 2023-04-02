package org.project;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class ElementTest {

  // Test for getCurrentFrame() and setCurrentFrame()
  @Test
  public void testGetSetCurrentFrame() {
    Element element = new ConcreteElement();
    element.setCurrentFrame(5);
    assertEquals(5, element.getCurrentFrame());
  }

  // Test for getName() and setName()
  @Test
  public void testGetSetName() {
    Element element = new ConcreteElement();
    element.setName("Test Element");
    assertEquals("Test Element", element.getName());
  }

  // Test for getCollision() and setCollision()
  @Test
  public void testGetSetCollision() {
    Element element = new ConcreteElement();
    element.setCollision(true);
    assertTrue(element.getCollision());
  }

  // Test for getWorldX() and setWorldX()
  @Test
  public void testGetSetWorldX() {
    Element element = new ConcreteElement();
    element.setWorldX(10);
    assertEquals(10, element.getWorldX());
  }

  // Test for getWorldY() and setWorldY()
  @Test
  public void testGetSetWorldY() {
    Element element = new ConcreteElement();
    element.setWorldY(20);
    assertEquals(20, element.getWorldY());
  }

  // Test for getSolidArea()
  @Test
  public void testGetSolidArea() {
    Element element = new ConcreteElement();
    assertEquals(new Rectangle(0, 0, 48, 48), element.getSolidArea());
  }

  // Concrete subclass of Element for testing
  private class ConcreteElement extends Element {
    // No additional functionality needed
  }
}