package org.example;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;

/**
 * The player that the user can control.
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class Player implements KeyListener {

  public boolean upPressed, downPressed, leftPressed, rightPressed; // keys

  @Override
  public void keyTyped(KeyEvent e) {}  // not using this one garbage

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_W) {
      upPressed = true;
    }
    if (code == KeyEvent.VK_S) {
      downPressed = true;
    }
    if (code == KeyEvent.VK_A) {
      leftPressed = true;
    }
    if (code == KeyEvent.VK_D) {
      rightPressed = true;
    }
    updateDirection(e);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_W) {
      upPressed = false;
    }
    if (code == KeyEvent.VK_S) {
      downPressed = false;
    }
    if (code == KeyEvent.VK_A) {
      leftPressed = false;
    }
    if (code == KeyEvent.VK_D) {
      rightPressed = false;
    }
//    System.out.println(currentDirection);
  }

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

  public BufferedImage down, up, right, left;


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

  public void updateDirection(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_A) {
      currentDirection = directions.LEFT;
    } else if (code == KeyEvent.VK_D) {
      currentDirection = directions.RIGHT;
    } else if (code == KeyEvent.VK_W) {
      currentDirection = directions.UP;
    } else if (code == KeyEvent.VK_S) {
      currentDirection = directions.DOWN;
    }
  }

  public void getPlayerImage() {
    try{
      down = ImageIO.read(new FileInputStream("res/player/Player_DRight.png"));
      up = ImageIO.read(new FileInputStream("res/player/Player_DRight.png"));
      left = ImageIO.read(new FileInputStream("res/player/Player_DLeft.png"));
      right = ImageIO.read(new FileInputStream("res/player/Player_DRight.png"));
//      downRight = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Player_DRight.png"));
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
    if (upPressed == true) {
      y -= playerSpeed;
    } else if (downPressed == true) {
      y += playerSpeed;
    } else if (rightPressed == true) {
      x += playerSpeed;
    } else if(leftPressed == true) {
      x -= playerSpeed;
    }
  }

  // to draw the player sprite
  public void draw(Graphics2D g2, GamePanel p) {
    BufferedImage image = null;

    switch(currentDirection) {
      case UP:
        image = up;
        break;
      case DOWN:
        image = down;
        break;
      case LEFT:
        image = left;
        break;
      case RIGHT:
        image = right;
        break;
      default:
        break;
    }
    g2.drawImage(image, x, y, p.tileSize, p.tileSize, null);

    // below is the stupid code for rectangle I used for testing
//    g2.setColor(Color.white);
//    g2.fillRect(x, y, p.tileSize, p.tileSize);
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

    gamePanel.startGameThread();
  }
}
