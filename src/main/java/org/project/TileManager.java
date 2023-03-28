package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

/**
 * Manages the static elements on the map
 *
 * @author Nathan Bartyuk
 * @version 2023-03-28
 */
public class TileManager {
  GamePanel gp;
  public Tile[] tiles;
  public int[][] map;
  
  /**
   * Constructs a new TileManager object
   * @param gp the GamePanel it belongs to
   */
  public TileManager(GamePanel gp) {
    this.gp = gp;
    tiles = new Tile[10];
    map = new int[GamePanel.MAP_COL][GamePanel.MAP_ROW];
    getTileImage();
    loadMap(gp);
  }
  
  /** Reads the map and gets assets for each tile */
  public void getTileImage() {
    try {
      tiles[0] = new Tile();
      tiles[0].sprite = ImageIO.read(new FileInputStream("assets/mapData/tiles/grass.png"));
      
      tiles[1] = new Tile();
      tiles[1].sprite = ImageIO.read(new FileInputStream("assets/mapData/tiles/wall.png"));
      tiles[1].collision = true;
      
      tiles[2] = new Tile();
      tiles[2].sprite = ImageIO.read(new FileInputStream("assets/mapData/tiles/water.png"));
      tiles[2].collision = true;
      
      tiles[3] = new Tile();
      tiles[3].sprite = ImageIO.read(new FileInputStream("assets/mapData/tiles/earth.png"));
      
      tiles[4] = new Tile();
      tiles[4].sprite = ImageIO.read(new FileInputStream("assets/mapData/tiles/tree.png"));
      tiles[4].collision = true;
      
      tiles[5] = new Tile();
      tiles[5].sprite = ImageIO.read(new FileInputStream("assets/mapData/tiles/sand.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Parses map assets from file
   * @param gp the GamePanel to display to
   */
  public void loadMap(GamePanel gp) {
    try {
      InputStream is = new FileInputStream("assets/mapData/maps/map2.txt");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));

      int col = 0;
      int row = 0;

      while (col< GamePanel.MAP_COL && row < GamePanel.MAP_ROW) {
        String line = br.readLine();
        String[] numbers = line.split(" ");
        while(col < GamePanel.MAP_COL) {
          int num = Integer.parseInt(numbers[col]);
          map[col][row] = num;
          col++;
        }
        if (col == GamePanel.MAP_COL) {
          col = 0;
          row++;
        }
      }
      br.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * Draws the map to screen
   * @param g2 the GamePanel to draw to
   */
  public void draw(Graphics2D g2) {
    int worldCol = 0;
    int worldRow = 0;

    while(worldCol < GamePanel.MAP_COL && worldRow < GamePanel.MAP_ROW) {
      int tileNum = map[worldCol][worldRow];
      int worldX = worldCol * GamePanel.TILE_SIZE;
      int worldY = worldRow * GamePanel.TILE_SIZE;
      int screenX = worldX - gp.player.worldX + gp.player.screenX;
      int screenY = worldY - gp.player.worldY + gp.player.screenY;
      
      if (worldX + GamePanel.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
          worldX - GamePanel.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
          worldY + GamePanel.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
          worldY - GamePanel.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
        g2.drawImage(tiles[tileNum].sprite, screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
      }
      
      worldCol++;
      if (worldCol == GamePanel.MAP_COL) {
        worldCol = 0;
        worldRow++;
      }
    }
  }
}
