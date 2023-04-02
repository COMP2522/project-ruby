package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Monster class for Beast is slated to be in the same vein as NPC.
 * The collision for this end will revolve around the player's lives.
 * Entity class is extended by this monster class, and al following monster classes.
 * Entity, GamePanel, ObjectHandler and CollisionDetector will all require code
 * May use pathfinding algorithm if I can develop it to be functional.
 * Update the coordinates for future use to have it in different sections of the map to guard rubies.
 *
 * @author Amrit Singh
 * @version 2023-03-27
 */

public class Monster extends Entity {
  public int posX;
  public int posY;

  public Monster(GamePanel gp, int posX, int posY) {
    super(gp);
    type = 2;
    speed = 1;
    hitbox = new Rectangle();
    hitbox.x = 8;
    hitbox.y = 8;
    hitboxDefaultX = hitbox.x;
    hitboxDefaultY = hitbox.y;
    hitbox.width = 32;
    hitbox.height = 32;
    this.posX = posX;
    this.posY = posY;
    direction = directions.DOWN;
    getImage();
  }

  public void getImage() {
    try {
      downR = ImageIO.read(new FileInputStream("assets/monsters/beast_down_right.png"));
      downL = ImageIO.read(new FileInputStream("assets/monsters/beast_down_left.png"));
      upR = ImageIO.read(new FileInputStream("assets/monsters/beast_up_right.png"));
      upL = ImageIO.read(new FileInputStream("assets/monsters/beast_up_left.png"));
      leftR = ImageIO.read(new FileInputStream("assets/monsters/beast_left_right.png"));
      leftL = ImageIO.read(new FileInputStream("assets/monsters/beast_left_left.png"));
      rightR = ImageIO.read(new FileInputStream("assets/monsters/beast_right_right.png"));
      rightL = ImageIO.read(new FileInputStream("assets/monsters/beast_right_left.png"));
    } catch (IOException e) {
      System.out.println("Image can't be read ...");
      e.printStackTrace();
    }
  }
  
}
