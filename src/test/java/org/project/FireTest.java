package org.project;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FireTest {

  // Test for setImage() and getImage()
  @Test
  public void testSetGetImage() {
    Fire fire = new Fire();
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File("assets/test/test_fire.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    fire.setImage(image);
    assertEquals(image, fire.getImage());
  }

  // Test for getCurrentFrame() and setCurrentFrame()
  @Test
  public void testGetSetCurrentFrame() {
    Fire fire = new Fire();
    fire.setCurrentFrame(3);
    assertEquals(3, fire.getCurrentFrame());
  }

  // Test for getName() and setName()
  @Test
  public void testGetSetName() {
    Fire fire = new Fire();
    fire.setName("Test Fire");
    assertEquals("Test Fire", fire.getName());
  }

  // Test for getCollision() and setCollision()
  @Test
  public void testGetSetCollision() {
    Fire fire = new Fire();
    fire.setCollision(false);
    assertFalse(fire.getCollision());
  }

  // Test for getWorldX() and setWorldX()
  @Test
  public void testGetSetWorldX() {
    Fire fire = new Fire();
    fire.setWorldX(10);
    assertEquals(10, fire.getWorldX());
  }

  // Test for getWorldY() and setWorldY()
  @Test
  public void testGetSetWorldY() {
    Fire fire = new Fire();
    fire.setWorldY(20);
    assertEquals(20, fire.getWorldY());
  }

  // Test for getSolidArea()
  @Test
  public void testGetSolidArea() {
    Fire fire = new Fire();
    assertEquals(new Rectangle(0, 0, 48, 48), fire.getSolidArea());
  }

  // Test for draw()
  @Test
  public void testDraw() {
    Fire fire = new Fire();
    GamePanel gamePanel = new GamePanel();
    BufferedImage image = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = image.createGraphics();
    g2.setBackground(Color.BLACK);
    g2.clearRect(0, 0, 48, 48);
    fire.draw(g2, gamePanel);
    assertNotNull(fire.getImage());
  }
}
