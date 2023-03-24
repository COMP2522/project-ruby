package org.project;

import org.jetbrains.annotations.NotNull;
import processing.core.PGraphics;
import processing.core.PImage;

import javax.imageio.ImageIO;
import java.io.*;

/**
 * Defines the game map, which consists of both a Tile array and Sprite array
 *
 * @author Nathan Bartyuk
 * @version 2023-02-07
 */
public class Map {

  public Tile[] tiles; // stores the different kinds of tiles that can be displayed

  int[][] map;
  
  
  public Map(SaveState save) {}
  
  public Map(Window window) {
    tiles = new Tile[10];
    map = new int[window.maxScreenCol][window.maxScreenRow];
    getTileImage();
    loadMap(window);
  }

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
  
  public void loadMap(@NotNull Window window) {
    try {
      InputStream is = new FileInputStream("assets/mapData/maps/map1.txt");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
    
      int col = 0;
      int row = 0;
    
      while (col< window.maxScreenCol && row < window.maxScreenRow) {
        String line = br.readLine();
        String[] numbers = line.split(" ");
        while(col < window.maxScreenCol) {
          int num = Integer.parseInt(numbers[col]);
          map[col][row] = num;
          col++;
        }
        if (col == window.maxScreenCol) {
          col = 0;
          row++;
        }
      }
      br.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  
  public void draw(PGraphics g, Window window) {
    int col = 0;
    int row = 0;
    int x = 0;
    int y = 0;
  
    while(col < window.maxScreenCol && row < window.maxScreenRow) {
      int tileNum = map[col][row];
      g.image(new PImage(tiles[tileNum].sprite), x, y, window.tileSize, window.tileSize);
      col++;
      x += window.tileSize;
      if (col == window.maxScreenCol) {
        col = 0;
        x = 0;
        row++;
        y += window.tileSize;
      }
    }
  }
}
