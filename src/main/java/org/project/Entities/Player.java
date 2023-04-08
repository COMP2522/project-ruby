package org.project.Entities;

import org.project.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

import static org.project.SystemVariables.*;

/**
 * The player that the user can control.
 * Contains a bunch of methods to update player position, status, sprites and interaction
 * with world components in Ruby Rush.
 *
 * @author Abhishek Chouhan
 * @version 2023-02-07
 */
public class Player extends Entity {

  // keyHandler instance ( can be public because they are no attributes in keyHandler to be modified)
  public final KeyHandler handler;
  private static Player instance;

  // Player stats
  public static final int MAX_LIVES = 6;
  
  private status currentStatus;
  private int currentLives;
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

  /**
   * sets up all image instances of the player.
   */
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

  /**
   * manages the position, sprite and all interactions of the player.
   * Utilizes a bunch of helper and modular functions to achieve above functionality.
   * @param gp the window Instance in which the player is being drawn in
   * @param kh the keyHandler Instance taking care of all inputs
   */
  public void update(GamePanel gp, KeyHandler kh) {
    // if a keyEvent occurred, update player values
    if (kh.upPressed || kh.downPressed || kh.leftPressed || kh.rightPressed) {
      // update the direction of player
      updateDirection(kh);

      // check collision with tile
      collision = false;
      gp.cDetector.checkTile(this);
  
      // Check collision/Interaction with objects
      int objectIndex = gp.cDetector.checkObject(this, true);
      pickupObject(objectIndex, gp);
  
      // Check collision with NPC
      int npcIndex = gp.cDetector.checkEntityCollide(this, gp.npc);
      interactNPC(npcIndex);
  
      // Check collision with Monster
      int monsterIndex = gp.cDetector.checkEntityCollide(this, gp.monster);
      interactMonster(monsterIndex);

      // update the position of the player on world map
      updatePosition();

      // update to next sprite the player should be drawn as
      updateSprite();

    }
  }

  /**
   * method used to update the direction of the player.
   * This function uses the input caught by the keyHandler class (stored as a static variable in kh)
   * By accessing that variable we know what the last key pressed was and update direction accordingly.
   * @param kh the keyHandler instance
   */
  private void updateDirection(KeyHandler kh) {
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

  /**
   * helper function to update player sprite to next frame to animate player movement.
   * Utilized in update method.
   */
  private void updateSprite() {
    /* loop between the frames of the player to animate*/
    spriteCounter++; // increment to count how many frames this sprite has been drawn already
    if (spriteCounter > 14) { // change sprite to be drawn after every 14 images drawn
      // switch sprites
      if (spriteNum == 1) {
        spriteNum = 2;
      } else if (spriteNum == 2) {
        spriteNum = 1;
      }
      spriteCounter = 0; // reset sprite Counter to 0 to count how many times the next sprite's been drawn
    }
    // draw 60 frames of INVINCIBLE player, that is show player is invincible for 1 sec after taking damage.
    else if (invincible) {
      invincibleCounter++;
      if (invincibleCounter > 60) { // 60 Frames per second hence player will be shown invincible for 1 sec
        invincible = false;
        invincibleCounter = 0;
      }
    }
  }

  /**
   * helper function to update player position.
   * Utilized in update method.
   */
  private void updatePosition() {
    // update position on worldMap if player is not colliding
    if (!collision) {
      if (direction == directions.LEFT) {
        worldX = worldX - speed;
      } else if (direction == directions.RIGHT) {
        worldX = worldX + speed;
      } else if (direction == directions.UP) {
        worldY = worldY - speed;
      } else {
        worldY = worldY + speed;
      }
    }
  }
  
  /**
   * Defines object collision/pickingUp behaviour of player
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
