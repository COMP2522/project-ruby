package org.example;

/**
 * Defines the game map, which consists of both a Tile array and Sprite array
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class Map {
  public static int MAPSIZE = 20;

  public Tile[][] tileMap = new Tile[MAPSIZE][MAPSIZE];
  public Sprite[][] spriteMap = new Sprite[MAPSIZE][MAPSIZE];


  public Map() {}
  public Map(SaveState save) {}

}
