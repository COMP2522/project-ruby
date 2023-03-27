package org.sourceCode;

import static org.sourceCode.Entity.directions.*;

public class Collision_Detection {
  GamePanel gp;


  public Collision_Detection(GamePanel gp) {
    this.gp = gp;
  }

  public void checkTile(Entity e) {
    int leftX = e.x + e.solidArea.x;
    int rightX = e.x + e.solidArea.x + e.solidArea.width;
    int upY = e.y + e.solidArea.y;
    int downY = e.y + e.solidArea.y + e.solidArea.height;

    int leftCol = leftX / gp.tileSize;
    int rightCol = rightX / gp.tileSize;
    int topRow = upY / gp.tileSize;
    int downRow = downY / gp.tileSize;

    int tileNum1, tileNum2;

      //checking the player's direction
      if (e.currentDirection == UP) {
        topRow = (upY - e.speed) / gp.tileSize;
        tileNum1 = gp.map1.map[leftCol][topRow];
        tileNum2 = gp.map1.map[rightCol][topRow];
        //if one of this is true that means player is hitting the wall.
        if (gp.map1.tiles[tileNum1].collision == true || gp.map1.tiles[tileNum2].collision == true) {
          e.collisionOn = true;
        }
      } else if (e.currentDirection == DOWN) {
        downRow = (downY + e.speed) / gp.tileSize;
        tileNum1 = gp.map1.map[leftCol][downRow];
        tileNum2 = gp.map1.map[rightCol][downRow];
        //if one of this is true that means player is hitting the wall.
        if (gp.map1.tiles[tileNum1].collision == true || gp.map1.tiles[tileNum2].collision == true) {
          e.collisionOn = true;
        }
      } else if (e.currentDirection == LEFT) {
        leftCol = (leftX - e.speed) / gp.tileSize;
        tileNum1 = gp.map1.map[leftCol][topRow];
        tileNum2 = gp.map1.map[rightCol][downRow];
        //if one of this is true that means player is hitting the wall.
        if (gp.map1.tiles[tileNum1].collision == true || gp.map1.tiles[tileNum2].collision == true) {
          e.collisionOn = true;
        }
      } else if (e.currentDirection == RIGHT) {
        rightCol = (rightX + e.speed) / gp.tileSize;
        tileNum1 = gp.map1.map[leftCol][topRow];
        tileNum2 = gp.map1.map[rightCol][downRow];
        //if one of this is true that means player is hitting the wall.
        if (gp.map1.tiles[tileNum1].collision == true || gp.map1.tiles[tileNum2].collision == true) {
          e.collisionOn = true;
        }
      }
  }
  public int checkObject(Player p, boolean player) {
    int index = 999;
    for(int i = 0; i < gp.objects.length; i++) {
      if(gp.objects[i] != null) {
        //get entity/player's solid area position
        p.solidArea.x = p.x + p.solidArea.x;
        p.solidArea.y = p.y + p.solidArea.y;
        //get teh object's solid area position
        gp.objects[i].solidArea.x = gp.objects[i].x + gp.objects[i].solidArea.x;
        gp.objects[i].solidArea.y = gp.objects[i].y + gp.objects[i].solidArea.y;

        if (p.currentDirection == UP) {
          p.solidArea.y -= p.speed;
          if(p.solidArea.intersects(gp.objects[i].solidArea)){
            System.out.println("up collision");
            if(gp.objects[i].collision == true) {
              p.collisionOn = true;
            }
            if(player == true) {
              index = i;
            }
          }
        } else if (p.currentDirection == DOWN) {
          p.solidArea.y += p.speed;
          if(p.solidArea.intersects(gp.objects[i].solidArea)){
            System.out.println("down collision");
            if(gp.objects[i].collision == true) {
              p.collisionOn = true;
            }
            if(player == true) {
              index = i;
            }
          }
        } else if (p.currentDirection == LEFT) {
          p.solidArea.x -= p.speed;
          if(p.solidArea.intersects(gp.objects[i].solidArea)){
            System.out.println("left collision");
            if(gp.objects[i].collision == true) {
              p.collisionOn = true;
            }
            if(player == true) {
              index = i;
            }
          }

        } else if (p.currentDirection == RIGHT) {
          p.solidArea.x += p.speed;
          if(p.solidArea.intersects(gp.objects[i].solidArea)){
            System.out.println("right collision");
            if(gp.objects[i].collision == true) {
              p.collisionOn = true;
            }
            if(player == true) {
              index = i;
            }
          }
        }
        p.solidArea.x = p.solidAreaDefaultX;
        p.solidArea.y = p.solidAreaDefaultY;

        gp.objects[i].solidArea.x = gp.objects[i].solidAreaDefaultX;
        gp.objects[i].solidArea.y = gp.objects[i].solidAreaDefaultY;

      }
    }
    return index;
  }
}
