package org.project;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.Rectangle;

import static org.project.Entity.directions.*;

/**
 * The player that the user can control.
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class Player extends Entity {

  private enum status {ALIVE, DEAD}
  private static final int MAXLIVES = 6;
  
  public KeyHandler handler;

  private int maxLife;

  public int currentLives;
  private int currentRubies;
  private status currentStatus;

  private Sound running;

  private static Player instance = null;
  
  @Override
  public void setAction() {}
  
  // Sets up fresh player upon starting a new game.
  private Player(GamePanel gp, KeyHandler kh) {
    super(gp);
    this.gp = gp;
    this.handler = kh;

    //initializing the running sound
    running = new Sound();
    running.setFile(4);

    this.worldX = GamePanel.TILE_SIZE * 37;
    this.worldY = GamePanel.TILE_SIZE * 9;
    this.screenX = gp.screenWidth/2 - GamePanel.TILE_SIZE/2;
    this.screenY = gp.screenHeight/2 - GamePanel.TILE_SIZE/2;
    this.solidArea = new Rectangle(8,16,32,32);
    
    this.speed = 5;
    this.direction = directions.DOWN; // initial direction of the player
    
    this.currentLives = MAXLIVES;
    this.currentRubies = 0;
    this.currentStatus = status.ALIVE;
    getPlayerImage();
  }

  /**
   * method to get an instance of the player class from the private constructor.
   * This method ensures only one instance of player can exist at a time.
   * @param gp the GamePanel used in the game thread
   * @param kh the keyhandler object handling all key inputs
   * @return new player object if new instance, else return previously instantiated instance.
   */
  public static Player getInstance(GamePanel gp, KeyHandler kh) {
    if (instance == null) {
      instance = new Player(gp, kh);
    }
    return instance;
  }
  

  public void updateDirection(KeyHandler kh) {
    if (kh.leftPressed) {
      direction = LEFT;
    } else if (kh.rightPressed) {
      direction = RIGHT;
    } else if (kh.upPressed) {
      direction = UP;
    } else {
      direction = DOWN;
    }
  }

  public void getPlayerImage() {
    try{
      downR = ImageIO.read(new FileInputStream("assets/player/Player_DRight.png"));
      downL = ImageIO.read(new FileInputStream("assets/player/Player_DLeft.png"));
      upR = ImageIO.read(new FileInputStream("assets/player/Player_URight.png"));
      upL = ImageIO.read(new FileInputStream("assets/player/Player_ULeft.png"));
      leftR = ImageIO.read(new FileInputStream("assets/player/Player_DRight.png"));
      leftL = ImageIO.read(new FileInputStream("assets/player/Player_DLeft.png"));
      rightR = ImageIO.read(new FileInputStream("assets/player/Player_DRight.png"));
      rightL = ImageIO.read(new FileInputStream("assets/player/Player_DLeft.png"));
    } catch(IOException e) {
      System.out.println("Image can't be read");
      e.printStackTrace();
    }
  }

  public void updateStatus(char stat) {
    if (stat == 'd') {
      currentStatus = status.DEAD;
    }
  }

  public int updateLives(int damage) {
    currentLives -= damage;
    return currentLives;
  }

  /**
   * returns the current number of lives player has.
   * @return currentLives
   */
  public int getLives() {
    return currentLives;
  }

//  public int updateRubies(boolean touch) {
//    if (touch)
//      currentRubies++;
//    return currentRubies;
//  }

  /**
   * returns the current number of rubies the player possesses.
   * @return the current number of rubies.
   */
  public int getCurrentRubies() {
    return currentRubies;
  }

  /**
   * Returns current lives of Player.
   * @return currentLives, an int
   */
  public int getCurrentLives() {
    return this.currentLives;
  }

  public void update(GamePanel gp, KeyHandler kh){
    if (kh.upPressed || kh.downPressed || kh.leftPressed || kh.rightPressed) {
//      running.play();
      updateDirection(kh);
      //checking for collision with the window boundary
      if (worldX < 0) { worldX = 0; }
      if (worldX + 48 >= gp.mapWidth) { worldX = gp.mapWidth - 48; }
      if (worldY < 0) { worldY = 0; }
      if (worldY + 48 >= gp.mapHeight) { worldY = gp.mapHeight - 48; }

      //checking tile collision
      collision = false;
      gp.cDetector.checkTile(this);

      //this is where the collision with the player is detected.
      int objectIndex = gp.cDetector.checkObject(this,true);
      pickupObject(objectIndex, gp);

      // Checking NPC collision
      int npcIndex = gp.cDetector.checkEntityCollide(this, gp.npc);
      interactNPC(npcIndex);

      // Checking monster collision
      int monsterIndex = gp.cDetector.checkEntityCollide(this, gp.monster);
      interactMonster(monsterIndex);

      if (!collision) {
        if (direction == LEFT) {
          worldX -= speed;
        } else if (direction == RIGHT) {
          worldX += speed;
        } else if (direction == UP) {
          worldY -= speed;
        } else {
          worldY += speed;
        }
      }

      spriteCounter++;
      if(spriteCounter > 14) {
        if (spriteNum == 1) {
          spriteNum = 2;
        } else if (spriteNum == 2) {
          spriteNum = 1;
        }
        spriteCounter = 0;
      }
    } else {
//      running.stop();
      if (invincible == true) {
        invincibleCounter ++;
        if (invincibleCounter > 60) {
          invincible = false;
          invincibleCounter = 0;
        }
      }
    }
  }

  /**
   * method to show interaction between player and object
   * @param index
   * @param gp
   */
  public void pickupObject(int index, GamePanel gp){
    if(index != 999) {
      String objectName = gp.elements[index].getName();
  
      switch (objectName) {
        case "Ruby" -> {
          gp.playSE(1);
          currentRubies++;
          gp.elements[index] = null;
          gp.ui.showMessage("You got a ruby");
          currentLives -= 1;
//          System.out.println("Rubies: " + currentRubies);
        }
        //this is where the door is removed from the array.
        case "Door" -> {
          if (currentRubies > 1) { // door can only be opened if the player has at least 1 ruby
            gp.playSE(2);
            gp.elements[index] = null;
            gp.ui.showMessage("You opened a door");
            currentRubies--;
          }
          else {
            gp.ui.showMessage("You need a ruby to do this");
          }
        }
        case "PowerUp" -> {
          gp.playSE(3);
          speed += 2;
          gp.elements[index] = null;
          gp.ui.showMessage("Speed mode ON");
        }
        case "Fire" -> {
          gp.ui.showMessage("Fire Hazard!!!");
        }
      }
    }
  }

  /**
   * method to handle player interaction with NPC- docile characters.
   * @param index the hit area passed by index******
   */
  public void interactNPC(int index) {
    if(index != 999) {
      System.out.println("Colliding with NPC!");
    }
  }

  /**
   * method to handle player interaction with hostile characters.
   * @param index the hit area passed by index******
   */
  public void interactMonster(int index) {
    if(index != 999) {
      if (invincible == false) {
        currentLives -= 1;
        invincible = true;
      }
      gp.ui.showMessage("Monster, RUN!");
    }
  }
}
