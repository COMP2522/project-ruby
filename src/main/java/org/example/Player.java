package org.example;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    updateDirection(e);
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


  // Sets up fresh player upon starting a new game.
  public Player() {
    this.currentLives = LIVES;
    this.currentRubies = 0;
    this.currentDirection = directions.RIGHT;
    this.currentStatus = status.ALIVE;
    this.x = 100;
    this.y = 100;
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

  public void updatePosition(int x, int y) {
    this.x = x;
    this.y = y;
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
