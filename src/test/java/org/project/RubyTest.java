package org.project;

import org.junit.jupiter.api.Test;
import org.project.Objects.Ruby;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class RubyTest {

  // Test for the constructor
  @Test
  public void testConstructor() {
    Ruby ruby = new Ruby();
    assertNotNull(ruby.getImage());
    assertTrue(ruby.getCollision());
  }

  // Test for setImage() and getImage()
  @Test
  public void testSetGetImage() {
    Ruby ruby = new Ruby();
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File("assets/test/test_ruby.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    ruby.setImage(image);
    assertEquals(image, ruby.getImage());
  }

  // Test for setCollision() and getCollision()
  @Test
  public void testSetGetCollision() {
    Ruby ruby = new Ruby();
    ruby.setCollision(false);
    assertFalse(ruby.getCollision());
  }

}
