package org.project;

/**
 * This class contains system-wide constants and enums used in the game.
 */
public record SystemVariables() {

  /** The size of each tile in pixels.*/
  public static final int TILE_SIZE = 48;

  /** The number of columns on the screen.*/
  public static final int SCREEN_COL = 16;

  /** The number of rows on the screen.*/
  public static final int SCREEN_ROW = 12;

  /** The number of columns on the game map.*/
  public static final int MAP_COL = 50;

  /** The number of rows on the game map.*/
  public static final int MAP_ROW = 50;

  /** The maximum value for an index in the game.*/
  public static final int MAX_INDEX = 999;

  /** The time interval, in nanoseconds, between each frame.*/
  public static final double DRAW_INTERVAL = 1000000000.0 / 60;

  /** An enum representing the four directions.*/
  public enum Directions {
    /** Left direction.*/
    LEFT,
    /** Right direction.*/
    RIGHT,
    /** Up direction.*/
    UP,
    /** Down direction.*/
    DOWN
  }

  /**An enum representing the status of an entity.*/
  public enum Status {
    /**The entity is alive.*/
    ALIVE,
    /**The entity is dead.*/
    DEAD
  }
}

