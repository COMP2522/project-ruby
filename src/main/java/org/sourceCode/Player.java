package org.sourceCode;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Rectangle;

import static org.sourceCode.Entity.directions.*;

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
  
  KeyHandler handler;

//  public int x; // initial position
//  public int y; // initial y position
//  public int playerSpeed = 4;
  private int currentLives;
  private int currentRubies;
//  public directions currentDirection;
  private status currentStatus;

//  public BufferedImage downR, downL, upR, upL, rightR, rightL, leftR, leftL;
//  public int spriteCounter = 0;
//  public int spriteNum = 1;


  // Sets up fresh player upon starting a new game.
  public Player(GamePanel gp, KeyHandler kh) {
    super(gp);
    this.gp = gp;
    this.handler = kh;
    this.worldX = 150;
    this.worldY = 50;
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
  

  public void updateDirection(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_A) {
      direction = LEFT;
    } else if (code == KeyEvent.VK_D) {
      direction = RIGHT;
    } else if (code == KeyEvent.VK_W) {
      direction = UP;
    } else if (code == KeyEvent.VK_S) {
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
//      if (upPressed) {
//        y -= playerSpeed;
//      } else if (downPressed) {
//        y += playerSpeed;
//      } else if (rightPressed) {
//        x += playerSpeed;
//      } else if(leftPressed) {
//        x -= playerSpeed;
//      }
      updateDirection(kh.e);

      //checking for collision with the window boundary
      if (worldX < 0) { worldX = 0; }
      if (worldX + 48 >= gp.getScreenWidth()) { worldX = gp.getScreenWidth() - 48; }
      if (worldY < 0) { worldY = 0; }
      if (worldY + 48 >= gp.getScreenHeight()) { worldY = gp.getScreenHeight() - 48; }

      //checking tile collision
      collisionOn = false;
      gp.cChecker.checkTile(this);
      int objectIndex = gp.cChecker.checkObject(this, true );
      pickupObject(objectIndex, gp);

      //if collision is false, player can move
      if (!collisionOn) {
        if (this.direction == UP) {
          worldY -= speed;
        } else if (this.direction == DOWN) {
          worldY += speed;
        } else if (this.direction == RIGHT) {
          worldX += speed;
        } else if (this.direction == LEFT) {
          worldX -= speed;
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
    }
  }

  public void pickupObject(int index, GamePanel gp){
    if(index != 999) {

      String objectName = gp.objects[index].name;

      switch(objectName) {
        case "Ruby":
          hasRuby++;
          gp.objects[index] = null;
          System.out.println("Rubies: " + hasRuby);
          break;
        case "Door":
          if(hasRuby > 1) {
            gp.objects[index] = null;
            hasRuby--;
          }
          break;
        case "Fast":
          speed += 2;
          gp.objects[index] = null;
          break;
      }
    }
  }
  
  
  
  public static void main(String[] args) {
    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setTitle("Ruby Rush");

    GamePanel gamePanel = new GamePanel();
    window.add(gamePanel);
    window.pack(); // causes the "window" declared in this player class main method to fit the preferred size and layouts of its subcomponents (i.e. become equal to gamePanel);

    // the window from this class can display a 16 * 12 tiles, where each tile is (48 by 48 pixels itself).
    window.setLocationRelativeTo(null);
    window.setVisible(true);

    gamePanel.setUpGame();
    gamePanel.startGameThread();
  }
}
