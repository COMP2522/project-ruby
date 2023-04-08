package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

import static org.project.SystemVariables.*;

/**
 * The player that the user can control.
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class Player extends Entity {

  public final KeyHandler handler;
  public static Player instance;

  // Player stats
  public static final int MAX_LIVES = 6;
  
  private status currentStatus;
  public int currentLives;
  private int currentRubies;

  // player does not need a setAction method because it is being controlled by the User
  // hence, we override it with an empty definition that is not being used.
  @Override
  public void setAction() {}

  /**
   * private constructor to initialize an instance of Player class
   * @param gp the window/ GamePanel in which the player is being drawn in
   * @param kh KeyHandler class that interprets input (to be utilized a lot to update player direction)
   */
  private Player(GamePanel gp, KeyHandler kh) {
    super(gp); // call the super constructor in entity class
    // initialize window variable and keyHandler instance
    this.gp = gp;
    this.handler = kh;

    // set Starting position and other attributes (hitbox, speed and initial direction)
    this.worldX = TILE_SIZE * 37; // 37 is starting x coordinate on our 50 * 50 map
    this.worldY = TILE_SIZE * 9;  // 9 is starting y coordinate on our 50 * 50 map

    // fix player to the center of the screen, it is not the player who's actually moving
    // it's the camera/ map :)
    this.screenX = (gp.screenWidth  - TILE_SIZE) / 2;  // divide by half to get to centre
    this.screenY = (gp.screenHeight - TILE_SIZE) / 2;

    /*
       hitbox values being initialized.
       the 10, 16 are the x and y coordinate of the hitbox relative to the player
       Specifically, these are from the top-left corner of the player's sprite.
     */
    this.hitbox = new Rectangle(10, 16,28,28);
    this.hitboxDefaultX = 10;
    this.hitboxDefaultY = 16;

    this.speed = 5;
    this.direction = directions.DOWN; // initial direction of the player
    
    this.currentLives = MAX_LIVES;
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
      direction = directions.LEFT;
    } else if (kh.rightPressed) {
      direction = directions.RIGHT;
    } else if (kh.upPressed) {
      direction = directions.UP;
    } else {
      direction = directions.DOWN;
    }
  }

  public void getPlayerImage() {
    try{
      downR = ImageIO.read(new FileInputStream("assets/player/PlayerDownR.png"));
      downL = ImageIO.read(new FileInputStream("assets/player/PlayerDownL.png"));
      upR = ImageIO.read(new FileInputStream("assets/player/PlayerUpR.png"));
      upL = ImageIO.read(new FileInputStream("assets/player/PlayerUpL.png"));
      leftR = ImageIO.read(new FileInputStream("assets/player/PlayerLeftR.png"));
      leftL = ImageIO.read(new FileInputStream("assets/player/PlayerLeftL.png"));
      rightR = ImageIO.read(new FileInputStream("assets/player/PlayerRightR.png"));
      rightL = ImageIO.read(new FileInputStream("assets/player/PlayerRightL.png"));
    } catch(IOException e) {
      System.out.println("Image can't be read");
      e.printStackTrace();
    }
  }

  public void update(GamePanel gp, KeyHandler kh) {
    if (kh.upPressed || kh.downPressed || kh.leftPressed || kh.rightPressed) {
      updateDirection(kh);
      //checking for collision with the window boundary
      if (worldX < 0) {
        worldX = 0;
      } else if (worldX + 48 >= gp.mapWidth) {
        worldX = gp.mapWidth - 48;
      }
      
      if (worldY < 0) {
        worldY = 0;
      } else if (worldY + 48 >= gp.mapHeight) {
        worldY = gp.mapHeight - 48;
      }
  
      //checking tile collision
      collision = false;
      gp.cDetector.checkTile(this);
  
      //this is where the collision with the player is detected.
      int objectIndex = gp.cDetector.checkObject(this, true);
      pickupObject(objectIndex, gp);
  
      // Checking NPC collision
      int npcIndex = gp.cDetector.checkEntityCollide(this, gp.npc);
      interactNPC(npcIndex);
  
      // Checking monster collision
      int monsterIndex = gp.cDetector.checkEntityCollide(this, gp.monster);
      interactMonster(monsterIndex);
  
      if (!collision) {
        if (direction == directions.LEFT) {
          worldX -= speed;
        } else if (direction == directions.RIGHT) {
          worldX += speed;
        } else if (direction == directions.UP) {
          worldY -= speed;
        } else {
          worldY += speed;
        }
      }
  
      spriteCounter++;
      if (spriteCounter > 14) {
        if (spriteNum == 1) {
          spriteNum = 2;
        } else if (spriteNum == 2) {
          spriteNum = 1;
        }
        spriteCounter = 0;
      } else if (invincible) {
        invincibleCounter++;
        if (invincibleCounter > 60) {
          invincible = false;
          invincibleCounter = 0;
        }
      }
    }
  }
  
  /**
   * Defines object collision behaviour
   * @param index The index of the player on the max
   * @param gp The game panel the player belongs to
   */
  public void pickupObject(int index, GamePanel gp) {
    if(index != this.indexMax) {
    
      Class<? extends Element> className = gp.elements[index].getClass();
  
      if (className.equals(Ruby.class)) {
        gp.playSE(1);
        currentRubies++;
        gp.elements[index] = null;
        gp.ui.showMessage("You got a ruby!");
        SaveState.getInstance().setSaveState(gp);
        SaveStateHandler.getInstance().save();
      } else if (className.equals(Door.class)) {
        if (currentRubies > 1) { // door can only be opened if the player has at least 1 ruby
          gp.playSE(2);
          gp.elements[index] = null;
          gp.ui.showMessage("You opened a door!");
          currentRubies--;
        } else {
          gp.ui.showMessage("You need more rubies for this door!");
        }
      } else if (className.equals(PowerUp.class)) {
        gp.playSE(3);
        speed += 2;
        gp.elements[index] = null;
        gp.ui.showMessage("Speed mode: ON");
      } else if (className.equals(Fire.class)) {
        gp.ui.showMessage("Fire Hazard!!");
        if (!invincible) {
          currentLives--;
        }
        invincible = true;
      }
    }
  }

  /**
   * Method to handle player interaction with NPC- docile characters.
   * @param index the hit area passed by index
   */
  public void interactNPC(int index) {
    if(index != this.indexMax) {
      gp.ui.showMessage("Watch where you're going!");
    }
  }

  /**
   * method to handle player interaction with hostile characters.
   * @param index the hit area passed by index
   */
  public void interactMonster(int index) {
    if(index != this.indexMax) {
      if (!invincible) {
        currentLives--;
        invincible = true;
      }
      gp.ui.showMessage("Monster.. RUN!!");
    }
  }
  
  // A bunch of getters and setters for instance variables
  public int getLives() {
    return currentLives;
  }
  public void setLives(int lives) {
    this.currentLives = lives;
  }
  public int getCurrentRubies() {
    return currentRubies;
  }
  public void setCurrentRubies(int rubies) {
    this.currentRubies = rubies;
  }
}
