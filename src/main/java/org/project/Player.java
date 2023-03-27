package org.project;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
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
    
    this.worldX = GamePanel.TILE_SIZE * 16;
    this.worldY = GamePanel.TILE_SIZE * 12;
    this.screenX = gp.screenWidth/2 - GamePanel.TILE_SIZE/2;
    this.screenY = gp.screenHeight/2 - GamePanel.TILE_SIZE/2;
    this.speed = 4;
    this.direction = directions.DOWN;
    
    this.currentLives = LIVES;
    this.currentRubies = 0;
    this.currentStatus = status.ALIVE;
    
    solidArea = new Rectangle();
    solidArea.x = 8;
    solidArea.y = 8;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 32;
    solidArea.height = 32;
    getPlayerImage();
  }
  

  public void updateDirection(KeyHandler kh) {
    if (kh.leftPressed) {
      direction = LEFT;
      if (!collision) {worldX -= speed;}
    } else if (kh.rightPressed) {
      direction = RIGHT;
      if (!collision) {worldX += speed;}
    } else if (kh.upPressed) {
      direction = UP;
      if (!collision) {worldY -= speed;}
    } else if (kh.downPressed) {
      direction = DOWN;
      if (!collision) {worldY += speed;}
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
      //checking for collision with the window boundary
      if (worldX < 0) { worldX = 0; }
      if (worldX + 48 >= gp.mapWidth) { worldX = gp.mapWidth - 48; }
      if (worldY < 0) { worldY = 0; }
      if (worldY + 48 >= gp.mapHeight) { worldY = gp.mapHeight - 48; }

      //checking tile collision
      collision = false;
      gp.cChecker.checkTile(this);
      int objectIndex = gp.cChecker.checkObject(this, true );
      pickupObject(objectIndex, gp);
  
      updateDirection(kh);
      
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
}
