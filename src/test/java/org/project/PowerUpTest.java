package org.project;

import org.junit.jupiter.api.Test;
import org.project.Objects.PowerUp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PowerUpTest {

  // Test for the constructor
  @Test
  public void testConstructor() {
    PowerUp powerup = new PowerUp();
    assertNotNull(powerup.getImage());
    assertTrue(powerup.getCollision());
  }

  // Test for setImage() and getImage()
  @Test
  public void testSetGetImage() {
    PowerUp powerup = new PowerUp();
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File("assets/test/test_powerup.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    powerup.setImage(image);
    assertEquals(image, powerup.getImage());
  }

  // Test for setCollision() and getCollision()
  @Test
  public void testSetGetCollision() {
    PowerUp powerup = new PowerUp();
    powerup.setCollision(false);
    assertFalse(powerup.getCollision());
  }

}
