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
  
  int hasRuby = 0;
  public enum status {ALIVE, DEAD}
  public static int LIVES = 3;
  
  public KeyHandler handler;
  
  private int currentLives;
  private int currentRubies;
  private status currentStatus;
  

  // Sets up fresh player upon starting a new game.
  public Player(GamePanel gp, KeyHandler kh) {
    super(gp);
    this.gp = gp;
    this.handler = kh;
    
    this.worldX = GamePanel.TILE_SIZE * 10;
    this.worldY = GamePanel.TILE_SIZE * 10;
    this.screenX = gp.screenWidth/2 - GamePanel.TILE_SIZE/2;
    this.screenY = gp.screenHeight/2 - GamePanel.TILE_SIZE/2;
    this.solidArea = new Rectangle(8,16,32,32);
    
    this.speed = 4;
    this.direction = directions.DOWN;
    
    this.currentLives = LIVES;
    this.currentRubies = 0;
    this.currentStatus = status.ALIVE;
    getPlayerImage();
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
      upR = ImageIO.read(new FileInputStream("assets/player/Player_DRight.png"));
      upL = ImageIO.read(new FileInputStream("assets/player/Player_DLeft.png"));
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

  public int updateRubies(boolean touch) {
    if (touch)
      currentRubies++;
    return currentRubies;
  }

  public void update(GamePanel gp, KeyHandler kh){
    if (kh.upPressed || kh.downPressed || kh.leftPressed || kh.rightPressed) {
      updateDirection(kh);
    
      //checking for collision with the window boundary
      if (worldX < 0) { worldX = 0; }
      if (worldX + 48 >= gp.mapWidth) { worldX = gp.mapWidth - 48; }
      if (worldY < 0) { worldY = 0; }
      if (worldY + 48 >= gp.mapHeight) { worldY = gp.mapHeight - 48; }

      //checking tile collision
      collision = false;
      gp.cDetector.checkTile(this);
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
      
      int objectIndex = gp.cDetector.checkObject(this, true );
      pickupObject(objectIndex, gp);

      // Checking NPC collision
      int npcIndex = gp.cDetector.checkObject(this, gp.npc);
      interactNPC(npcIndex);
      
      spriteCounter++;
      if(spriteCounter > 14) {
        if (spriteNum == 1) {
          spriteNum = 2;
        } else if (spriteNum == 2) {
          spriteNum = 1;
        }
        spriteCounter = 0;
      }
    }
  }

  public void pickupObject(int index, GamePanel gp){
    if(index != 999) {
      String objectName = gp.objects[index].name;
  
      switch (objectName) {
        case "Ruby" -> {
          hasRuby++;
          gp.objects[index] = null;
          System.out.println("Rubies: " + hasRuby);
        }
        case "Door" -> {
          if (hasRuby > 1) {
            gp.objects[index] = null;
            hasRuby--;
          }
        }
        case "Fast" -> {
          speed += 2;
          gp.objects[index] = null;
        }
      }
    }
  }

  public void interactNPC(int index) {
    if(index != 999) {
      System.out.println("Colliding with NPC!");
    }
  }
}
