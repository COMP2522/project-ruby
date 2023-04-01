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

  public Monster(GamePanel gp) {
    super(gp);
    speed = 1;
    solidArea = new Rectangle();
    solidArea.x = 8;
    solidArea.y = 8;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 32;
    solidArea.height = 32;
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

  public void setAction() {
    actionLockCounter++;
    // Adjust the number within the if statement to control NPC's directional change interval based on frames.
    if (actionLockCounter == 120) {
      Random random = new Random();
      int i = random.nextInt(100) + 1; // picks up a number from 1 to 100

      switch(i/25) {
        case 0:
          direction = directions.UP;
          break;
        case 1:
          direction = directions.DOWN;
          break;
        case 2:
          direction = directions.LEFT;
          break;
        case 3:
          direction = directions.RIGHT;
          break;
      }

      // Reset the counter so when the next direction is selected
      // It will have to await the duration of the interval before changing again.
      actionLockCounter = 0;
    }
  }
}
