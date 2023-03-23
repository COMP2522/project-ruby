package org.sourceCode;

import static org.sourceCode.Player.directions.*;

public class Collision_Detection {
  GamePanel gp;

  public Collision_Detection(GamePanel gp) {
    this.gp = gp;
  }

  public void checkTile(Player p) {
    int playerLeftX = p.x + p.solidArea.x;
    int playerRightX = p.x + p.solidArea.x + p.solidArea.width;
    int playerUpY = p.y + p.solidArea.y;
    int playerDownY = p.y + p.solidArea.y + p.solidArea.height;

    int playerLeftCol = playerLeftX / gp.tileSize;
    int playerRightCol = playerRightX / gp.tileSize;
    int playerTopRow = playerUpY / gp.tileSize;
    int playerDownRow = playerDownY / gp.tileSize;

    int tileNum1, tileNum2;

      //checking the player's direction
      if (p.currentDirection == UP) {
        playerTopRow = (playerUpY - p.playerSpeed) / gp.tileSize;
        tileNum1 = gp.map1.map[playerLeftCol][playerTopRow];
        tileNum2 = gp.map1.map[playerRightCol][playerTopRow];
        //if one of this is true that means player is hitting the wall.
        if (gp.map1.tiles[tileNum1].collision == true || gp.map1.tiles[tileNum2].collision == true) {
          p.collision = true;
        }
      } else if (p.currentDirection == DOWN) {
        playerDownRow = (playerDownY + p.playerSpeed) / gp.tileSize;
        tileNum1 = gp.map1.map[playerLeftCol][playerDownRow];
        tileNum2 = gp.map1.map[playerRightCol][playerDownRow];
        //if one of this is true that means player is hitting the wall.
        if (gp.map1.tiles[tileNum1].collision == true || gp.map1.tiles[tileNum2].collision == true) {
          p.collision = true;
        }
      } else if (p.currentDirection == LEFT) {
        playerLeftCol = (playerLeftX - p.playerSpeed) / gp.tileSize;
        tileNum1 = gp.map1.map[playerLeftCol][playerTopRow];
        tileNum2 = gp.map1.map[playerRightCol][playerDownRow];
        //if one of this is true that means player is hitting the wall.
        if (gp.map1.tiles[tileNum1].collision == true || gp.map1.tiles[tileNum2].collision == true) {
          p.collision = true;
        }
      } else if (p.currentDirection == RIGHT) {
        playerRightCol = (playerRightX + p.playerSpeed) / gp.tileSize;
        tileNum1 = gp.map1.map[playerLeftCol][playerTopRow];
        tileNum2 = gp.map1.map[playerRightCol][playerDownRow];
        //if one of this is true that means player is hitting the wall.
        if (gp.map1.tiles[tileNum1].collision == true || gp.map1.tiles[tileNum2].collision == true) {
          p.collision = true;
        }
      }
  }


}
