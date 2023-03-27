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

import static org.sourceCode.Player.directions.*;

/**
 * The player that the user can control.
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class Player extends Object {

  GamePanel gp;
  BufferedImage ashImage;
  public boolean upPressed, downPressed, leftPressed, rightPressed; // keys

  protected Rectangle solidArea;
  public int solidAreaDefaultX, solidAreaDefaultY;
  public boolean collision = false;
  int hasRuby = 0;

//  @Override
//  public void keyTyped(KeyEvent e) {}  // not using this one garbage
//
//  @Override
//  public void keyPressed(KeyEvent e) {
//    int code = e.getKeyCode();
//    if (code == KeyEvent.VK_W) {
//      upPressed = true;
//    }
//    if (code == KeyEvent.VK_S) {
//      downPressed = true;
//    }
//    if (code == KeyEvent.VK_A) {
//      leftPressed = true;
//    }
//    if (code == KeyEvent.VK_D) {
//      rightPressed = true;
//    }
//    updateDirection(e);
//  }
//
//  @Override
//  public void keyReleased(KeyEvent e) {
//    int code = e.getKeyCode();
//    if (code == KeyEvent.VK_W) {
//      upPressed = false;
//    }
//    if (code == KeyEvent.VK_S) {
//      downPressed = false;
//    }
//    if (code == KeyEvent.VK_A) {
//      leftPressed = false;
//    }
//    if (code == KeyEvent.VK_D) {
//      rightPressed = false;
//    }
////    System.out.println(currentDirection);
//  }

  public enum directions {LEFT, RIGHT, UP, DOWN}
  public enum status {ALIVE, DEAD}
  public static int LIVES = 3;

  public int x; // initial position
  public int y; // initial y position
  public int playerSpeed = 4;
  private int currentLives;
  private int currentRubies;
  public directions currentDirection;
  private status currentStatus;

  public BufferedImage downR, downL, upR, upL, rightR, rightL, leftR, leftL;
  public int spriteCounter = 0;
  public int spriteNum = 1;


  // Sets up fresh player upon starting a new game.
  public Player() {
    this.currentLives = LIVES;
    this.currentRubies = 0;
    this.currentDirection = directions.DOWN;
    this.currentStatus = status.ALIVE;
    solidArea = new Rectangle();
    solidArea.x = 0;
    solidArea.y = 0;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 32;
    solidArea.height = 32;
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

  public void updateDirection(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_A) {
      currentDirection = directions.LEFT;
    } else if (code == KeyEvent.VK_D) {
      currentDirection = directions.RIGHT;
    } else if (code == KeyEvent.VK_W) {
      currentDirection = UP;
    } else if (code == KeyEvent.VK_S) {
      currentDirection = directions.DOWN;
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

  public void updatePosition(GamePanel gp){
    if (upPressed || downPressed || leftPressed || rightPressed) {
//      if (upPressed) {
//        y -= playerSpeed;
//      } else if (downPressed) {
//        y += playerSpeed;
//      } else if (rightPressed) {
//        x += playerSpeed;
//      } else if(leftPressed) {
//        x -= playerSpeed;
//      }

      //checking for collision with the window boundary
      if (x < 0) {
        x = 0;
      }
      if (x + 48 >= gp.getScreenWidth()) {
        x = gp.getScreenWidth() - 48;
      }
      if (y < 0) {
        y = 0;
      }
      if (y + 48 >= gp.getScreenHeight()) {
        y = gp.getScreenHeight() - 48;
      }

        //checking tile collision
        collision = false;
        gp.cChecker.checkTile(this);

        int objectIndex = gp.cChecker.checkObject(this, true );
        pickupObject(objectIndex, gp);

      //if collision is false, player can move
      if (collision == false) {
        if (this.currentDirection == UP) {
          y -= playerSpeed;
        } else if (this.currentDirection == DOWN) {
          y += playerSpeed;
        } else if (this.currentDirection == RIGHT) {
          x += playerSpeed;
        } else if (this.currentDirection == LEFT) {
          x -= playerSpeed;
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

  // to draw the player sprite
  public void draw(Graphics2D g2, GamePanel p) {
    BufferedImage image = null;

    switch(currentDirection) {
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
    g2.drawImage(image, x, y, p.tileSize, p.tileSize, null);

    // below is the stupid code for rectangle I used for testing
//    g2.setColor(Color.white);
//    g2.fillRect(x, y, p.tileSize, p.tileSize);
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
          playerSpeed += 2;
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
