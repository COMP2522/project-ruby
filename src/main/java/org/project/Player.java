package org.project;

import javax.imageio.ImageIO;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * The player that the user can control.
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class Player extends PApplet {

  public boolean upPressed, downPressed, leftPressed, rightPressed; // keys
  
  public enum directions {LEFT, RIGHT, UP, DOWN}
  public enum status {ALIVE, DEAD}
  public static int LIVES = 3;
  
  public int x; // initial position
  public int y; // initial y position
  public final int playerSpeed = 4;
  private int currentLives;
  private int currentRubies;
  private directions currentDirection;
  private status currentStatus;
  
  public BufferedImage downR, downL, upR, upL, rightR, rightL, leftR, leftL;
  
  public int spriteCounter = 0;
  public int spriteNum = 1;


  public void move(int keyCode) {
    if (keyCode == PConstants.LEFT) {
      currentDirection = directions.LEFT;
      leftPressed = true;
    }
    if (keyCode == PConstants.RIGHT) {
      currentDirection = directions.RIGHT;
      rightPressed = true;
    }
    if (keyCode == PConstants.UP) {
      currentDirection = directions.UP;
      upPressed = true;
    }
    if (keyCode == PConstants.DOWN) {
      currentDirection = directions.DOWN;
      downPressed = true;
    }
  }
  
  public void stop(int keyCode) {
    if (keyCode == PConstants.LEFT) {
      leftPressed = false;
    }
    if (keyCode == PConstants.RIGHT) {
      rightPressed = false;
    }
    if (keyCode == PConstants.UP) {
      upPressed = false;
    }
    if (keyCode == PConstants.DOWN) {
      downPressed = false;
    }
  }


  // Sets up fresh player upon starting a new game.
  public Player() {
    this.currentLives = LIVES;
    this.currentRubies = 0;
    this.currentDirection = directions.DOWN;
    this.currentStatus = status.ALIVE;
    this.x = 150;
    this.y = 50;
    getPlayerImage();
  }

  // Sets up the player from an existing save.
  public Player(int x, int y, int rubies) {
    this.currentLives = LIVES;
    this.currentRubies = rubies;
    this.currentDirection = directions.LEFT;
    this.currentStatus = status.ALIVE;
    this.x = x;
    this.y = y;
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

  public void updatePosition() {
    if (upPressed || downPressed || leftPressed || rightPressed) {
      if (upPressed) {
        y -= playerSpeed;
      } else if (downPressed) {
        y += playerSpeed;
      } else if (rightPressed) {
        x += playerSpeed;
      } else if(leftPressed) {
        x -= playerSpeed;
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
  
  
  public void draw(PGraphics g, Window window) {
    BufferedImage image = null;
  
    switch (currentDirection) {
      case UP -> {
        if (spriteNum == 1) {image = upR;}
        if (spriteNum == 2) {image = upL;}
      }
      case DOWN -> {
        if (spriteNum == 1) {image = downR;}
        if (spriteNum == 2) {image = downL;}
      }
      case LEFT -> {
        if (spriteNum == 1) {image = leftR;}
        if (spriteNum == 2) {image = leftL;}
      }
      case RIGHT -> {
        if (spriteNum == 1) {image = rightR;}
        if (spriteNum == 2) {image = rightL;}
      }
      default -> {}
    }
    g.image(new PImage(image),x,y,window.tileSize,window.tileSize);
  }
  

  public static void main(String[] args) {
    println("At least it runs haha");
  }
}
