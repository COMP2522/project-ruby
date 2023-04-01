package org.project;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DoorTest {

  // Test for the constructor
  @Test
  public void testConstructor() {
    Door door = new Door();
    assertEquals("Door", door.getName());
    assertNotNull(door.getImage());
    assertTrue(door.getCollision());
  }

  // Test for setImage() and getImage()
  @Test
  public void testSetGetImage() {
    Door door = new Door();
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File("assets/test/test_door.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    door.setImage(image);
    assertEquals(image, door.getImage());
  }

  // Test for setCollision() and getCollision()
  @Test
  public void testSetGetCollision() {
    Door door = new Door();
    door.setCollision(false);
    assertFalse(door.getCollision());
  }

}
