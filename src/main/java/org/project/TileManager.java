package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import static org.project.SystemVariables.*;

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
  
  protected String mapPath = "assets/mapData/maps/map2.txt";
  protected String tilePath = "assets/mapData/tiles/";
  protected String[] tileName = {"grass.png", "wall.png", "water.png", "earth.png", "tree.png", "sand.png"};
  protected boolean[] tileCollide = {false, true, true, false, true, false};
  
  /**
   * Constructs a new TileManager object
   * @param gp the GamePanel it belongs to
   */
  public TileManager(GamePanel gp) {
    this.gp = gp;
    tiles = new Tile[10];
    map = new int[MAP_COL][MAP_ROW];
    getTileImage();
    loadMap();
  }
  
  /** Reads the map and gets assets for each tile */
  public void getTileImage() {
    try {
      for (int i = 0; i < 6; i++) {
        tiles[i] = new Tile();
        tiles[i].sprite = ImageIO.read(new FileInputStream(tilePath + tileName[i]));
        tiles[i].collision = tileCollide[i];
      }
    } catch (IOException e) {
      System.out.println("Invalid tile image");
      e.printStackTrace();
    }
  }
  
  /** Parses map assets from file */
  public void loadMap() {
    try {
      InputStream is = new FileInputStream(mapPath);
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      int col = 0;
      int row = 0;

      while (col< MAP_COL && row < MAP_ROW) {
        String line = br.readLine();
        String[] numbers = line.split(" ");
        while(col < MAP_COL) {
          int num = Integer.parseInt(numbers[col]);
          map[col][row] = num;
          col++;
        }
        if (col == MAP_COL) {
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
    
    // Only processes sprites in the parsed map
    while(worldCol < MAP_COL && worldRow < MAP_ROW) {
      int tileNum = map[worldCol][worldRow];
      int worldX = worldCol * TILE_SIZE;
      int worldY = worldRow * TILE_SIZE;
      int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
      int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();
      
      // Only draws sprites in the window view
      if (worldX + TILE_SIZE > gp.player.getWorldX() - gp.player.getScreenX() &&
          worldX - TILE_SIZE < gp.player.getWorldX() + gp.player.getScreenX() &&
          worldY + TILE_SIZE > gp.player.getWorldY() - gp.player.getScreenY() &&
          worldY - TILE_SIZE < gp.player.getWorldY() + gp.player.getScreenY()) {
        g2.drawImage(tiles[tileNum].sprite, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
      }
      
      worldCol++;
      if (worldCol == MAP_COL) {
        worldCol = 0;
        worldRow++;
      }
    }
  }
}
