package org.project;

public record SystemVariables() {
  public static final int TILE_SIZE = 48;
  public static final int SCREEN_COL = 16;
  public static final int SCREEN_ROW = 12;
  public static final int MAP_COL = 50;
  public static final int MAP_ROW = 50;
  public static final int MAX_INDEX = 999;
  public static final double DRAW_INTERVAL = 1000000000.0 / 60;
  public enum directions {LEFT, RIGHT, UP, DOWN}
  public enum status {ALIVE, DEAD}
}
