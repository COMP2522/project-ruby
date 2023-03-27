package org.sourceCode;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.nio.Buffer;
import java.util.Arrays;

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
//  public Map() {
//
//  }

  public Map(GamePanel gp) {
    tiles = new Tile[10];
    map = new int[gp.maxScreenCol][gp.maxScreenRow];
    getTileImage();
    loadMap(gp);
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
      tiles[3] = new Tile();
      tiles[3].sprite = ImageIO.read(new FileInputStream("assets/mapData/images/water.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void loadMap(@NotNull GamePanel gp) {
    try {
      InputStream is = new FileInputStream("assets/mapData/maps/map1.txt");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));

      int col = 0;
      int row = 0;

      while (col< gp.maxScreenCol && row < gp.maxScreenRow) {
        String line = br.readLine();
        String numbers[] = line.split(" ");
        while(col < gp.maxScreenCol) {
          int num = Integer.parseInt(numbers[col]);
          map[col][row] = num;
          col++;
        }
        if (col == gp.maxScreenCol) {
          col = 0;
          row++;
        }
      }
      br.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void draw(Graphics2D g2, GamePanel gp) {
//    g2.drawImage(tiles[1].sprite, 0, 0, gp.tileSize, gp.tileSize, null);  // null is image observer IDK what that is tho
    int col = 0;
    int row = 0;
    int x = 0;
    int y = 0;

    while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
      int tileNum = map[col][row];
      g2.drawImage(tiles[tileNum].sprite, x, y, gp.tileSize, gp.tileSize, null);
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

//  public static void main(String[] args) {
//    Map test = new Map();
//  }
}
