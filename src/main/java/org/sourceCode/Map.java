package org.sourceCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Defines the game map, which consists of both a Tile array and Sprite array
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class Map {
//  public static int MAPSIZE = 20;

  public Tile[] tiles; // stores the different kinds of tiles that can be displayed

  int map[][];

//  public Tile[][] tileMap = new Tile[MAPSIZE][MAPSIZE];
//  public Sprite[][] spriteMap = new Sprite[MAPSIZE][MAPSIZE];
  public Map() {

  }

  public Map(GamePanel gp) {
    tiles = new Tile[10];
    getTileImage();
    map = new int[gp.maxScreenCol][gp.maxScreenRow];
  }
  public Map(SaveState save) {}


  public void getTileImage() {
    try {
      tiles[0] = new Tile();
      tiles[0].sprite = ImageIO.read(new FileInputStream("assets/mapData/images/background.png"));
      tiles[1] = new Tile();
      tiles[1].sprite = ImageIO.read(new FileInputStream("assets/mapData/images/wall.png"));
      tiles[2] = new Tile();
      tiles[2].sprite = ImageIO.read(new FileInputStream("assets/mapData/images/bush.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D g2, GamePanel gp) {
//    g2.drawImage(tiles[1].sprite, 0, 0, gp.tileSize, gp.tileSize, null);  // null is image observer IDK what that is tho
    int col = 0;
    int row = 0;
    int x = 0;
    int y = 0;

    while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
      g2.drawImage(tiles[1].sprite, x, y, gp.tileSize, gp.tileSize, null);
      col++;
      x += gp.tileSize;
      if (col == gp.maxScreenCol) {
        col = 0;
        x = 0;
        row++;
        y += gp.tileSize;
      }
    }
  }
}
