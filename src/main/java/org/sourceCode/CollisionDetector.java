package org.sourceCode;

import static org.sourceCode.Entity.directions.*;

public class CollisionDetector {
  GamePanel gp;


  public CollisionDetector(GamePanel gp) {
    this.gp = gp;
  }

  public void checkTile(Entity entity) {
    int leftX = entity.worldX + entity.solidArea.x;
    int rightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
    int upY = entity.worldY + entity.solidArea.y;
    int downY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

    int leftCol = leftX / gp.tileSize;
    int rightCol = rightX / gp.tileSize;
    int topRow = upY / gp.tileSize;
    int downRow = downY / gp.tileSize;

    int tileNum1, tileNum2;

      //checking the player's direction
      if (entity.direction == UP) {
        topRow = (upY - entity.speed) / gp.tileSize;
        tileNum1 = gp.map1.map[leftCol][topRow];
        tileNum2 = gp.map1.map[rightCol][topRow];
        //if one of this is true that means player is hitting the wall.
        if (gp.map1.tiles[tileNum1].collision || gp.map1.tiles[tileNum2].collision) {
          entity.collisionOn = true;
        }
      } else if (entity.direction == DOWN) {
        downRow = (downY + entity.speed) / gp.tileSize;
        tileNum1 = gp.map1.map[leftCol][downRow];
        tileNum2 = gp.map1.map[rightCol][downRow];
        //if one of this is true that means player is hitting the wall.
        if (gp.map1.tiles[tileNum1].collision || gp.map1.tiles[tileNum2].collision) {
          entity.collisionOn = true;
        }
      } else if (entity.direction == LEFT) {
        leftCol = (leftX - entity.speed) / gp.tileSize;
        tileNum1 = gp.map1.map[leftCol][topRow];
        tileNum2 = gp.map1.map[rightCol][downRow];
        //if one of this is true that means player is hitting the wall.
        if (gp.map1.tiles[tileNum1].collision || gp.map1.tiles[tileNum2].collision) {
          entity.collisionOn = true;
        }
      } else if (entity.direction == RIGHT) {
        rightCol = (rightX + entity.speed) / gp.tileSize;
        tileNum1 = gp.map1.map[leftCol][topRow];
        tileNum2 = gp.map1.map[rightCol][downRow];
        //if one of this is true that means player is hitting the wall.
        if (gp.map1.tiles[tileNum1].collision || gp.map1.tiles[tileNum2].collision) {
          entity.collisionOn = true;
        }
      }
  }
  public int checkObject(Player p, boolean player) {
    int index = 999;
    for(int i = 0; i < gp.objects.length; i++) {
      if(gp.objects[i] != null) {
        //get entity/player's solid area position
        p.solidArea.x = p.worldX + p.solidArea.x;
        p.solidArea.y = p.worldY + p.solidArea.y;
        //get teh object's solid area position
        gp.objects[i].solidArea.x = gp.objects[i].x + gp.objects[i].solidArea.x;
        gp.objects[i].solidArea.y = gp.objects[i].y + gp.objects[i].solidArea.y;

        if (p.direction == UP) {
          p.solidArea.y -= p.speed;
          if(p.solidArea.intersects(gp.objects[i].solidArea)){
            System.out.println("up collision");
            if(gp.objects[i].collision) {
              p.collisionOn = true;
            }
            if(player) {
              index = i;
            }
          }
        } else if (p.direction == DOWN) {
          p.solidArea.y += p.speed;
          if(p.solidArea.intersects(gp.objects[i].solidArea)){
            System.out.println("down collision");
            if(gp.objects[i].collision) {
              p.collisionOn = true;
            }
            if(player) {
              index = i;
            }
          }
        } else if (p.direction == LEFT) {
          p.solidArea.x -= p.speed;
          if(p.solidArea.intersects(gp.objects[i].solidArea)){
            System.out.println("left collision");
            if(gp.objects[i].collision) {
              p.collisionOn = true;
            }
            if(player) {
              index = i;
            }
          }

        } else if (p.direction == RIGHT) {
          p.solidArea.x += p.speed;
          if(p.solidArea.intersects(gp.objects[i].solidArea)){
            System.out.println("right collision");
            if(gp.objects[i].collision) {
              p.collisionOn = true;
            }
            if(player) {
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
