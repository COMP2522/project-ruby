package org.example;

/**
 * The player that the user can control.
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class Player {
  public enum directions {LEFT, RIGHT, UP, DOWN}
  public enum status {ALIVE, DEAD}
  public static int LIVES = 3;

  private int x;
  private int y;
  private int currentLives;
  private int currentRubies;
  private directions currentDirection;
  private status currentStatus;


  // Sets up fresh player upon starting a new game.
  public Player() {
    this.currentLives = LIVES;
    this.currentRubies = 0;
    this.currentDirection = directions.LEFT;
    this.currentStatus = status.ALIVE;
  }

  // Sets up the player from an existing save.
  public Player(int rubies) {
    this.currentLives = LIVES;
    this.currentRubies = rubies;
    this.currentDirection = directions.LEFT;
    this.currentStatus = status.ALIVE;
  }

  public void updateDirection(char key) {
    if (key == 'l') {
      currentDirection = directions.LEFT;
    } else if (key == 'r') {
      currentDirection = directions.RIGHT;
    } else if (key == 'u') {
      currentDirection = directions.UP;
    } else if (key == 'd') {
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
}
