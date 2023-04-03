package org.project;

import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import static org.project.Entity.directions.*;

/**
 * The player that the user can control.
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class Player extends Entity {

  private enum status {ALIVE, DEAD}
  private static final int MAX_LIVES = 6;
  
  public KeyHandler handler;

  public int currentLives;
  private int currentRubies;
  private status currentStatus;
  
  private static Player instance = null;
  
  @Override
  public void setAction() {}
  
  // Sets up fresh player upon starting a new game.
  private Player(GamePanel gp, KeyHandler kh) {
    super(gp);
    this.gp = gp;
    this.handler = kh;

    //initializing the running sound
    Sound running = new Sound();
    running.setFile(4);

    this.worldX = GamePanel.TILE_SIZE * 37;
    this.worldY = GamePanel.TILE_SIZE * 9;
    this.screenX = gp.screenWidth/2 - GamePanel.TILE_SIZE/2;
    this.screenY = gp.screenHeight/2 - GamePanel.TILE_SIZE/2;
    this.hitbox = new Rectangle(8,24,24,32);
    
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

  @Override
  public void draw(Graphics2D g2) {
    BufferedImage image = null;
    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    if (worldX + GamePanel.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
        worldX - GamePanel.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
        worldY + GamePanel.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
        worldY - GamePanel.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
      switch(direction) {
        case UP:
          if (spriteNum == 1) {image = upR;}
          if (spriteNum == 2) {image = upL;}
          break;
        case DOWN:
          if (spriteNum == 1) {image = downR;}
          if (spriteNum == 2) {image = downL;}
          break;
        case LEFT:
          if (spriteNum == 1) {image = leftR;}
          if (spriteNum == 2) {image = leftL;}
          break;
        case RIGHT:
          if (spriteNum == 1) {image = rightR;}
          if (spriteNum == 2) {image = rightL;}
          break;
        default:
          break;
      }
    }

    if (invincible == true) {
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
    }
    g2.drawImage(image, screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);

    // Reset Opacity
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
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
  

  /**
   * returns the current number of rubies the player possesses.
   * @return the current number of rubies.
   */
  public int getCurrentRubies() {
    return currentRubies;
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
      String objectName = gp.elements[index].getName();
  
      switch (objectName) {
        case "Ruby" -> {
          gp.playSE(1);
          currentRubies++;
          gp.elements[index] = null;
          gp.ui.showMessage("You got a ruby!");
        }
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
          gp.ui.showMessage("Speed mode: ON");
        }
        case "Fire" -> {
          gp.ui.showMessage("Fire Hazard!!");
          if (!invincible) { currentLives--; }
          invincible = true;
        }
      }
    }
  }

  /**
   * Method to handle player interaction with NPC- docile characters.
   * @param index the hit area passed by index
   */
  public void interactNPC(int index) {
    if(index != this.indexMax) {
      System.out.println("Colliding with NPC!");
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
      gp.ui.showMessage("Monster, RUN!");
    }
  }


  /**
   * Sets Player variables from JSON playerData
   * @param playerData, JSONObject from SaveState
   */
  public void loadPlayerData(JSONObject playerData) {
    if (playerData == null) {
      throw new NullPointerException("Player object is null.");
    }
    this.worldX = ((Long) playerData.get("worldX")).intValue();
    this.worldY = ((Long) playerData.get("worldY")).intValue();
    this.speed = ((Long) playerData.get("speed")).intValue();
    int directionOrdinal = ((Long) playerData.get("direction")).intValue();
    this.direction = values()[directionOrdinal];
    this.spriteCounter = ((Long) playerData.get("spriteCounter")).intValue();
    this.spriteNum = ((Long) playerData.get("spriteNum")).intValue();
    this.currentLives= ((Long) playerData.get("lives")).intValue();
    this.currentRubies = ((Long) playerData.get("rubies")).intValue();
  }
}
