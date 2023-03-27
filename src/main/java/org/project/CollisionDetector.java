package org.project;

import static org.project.Entity.directions.*;

public class CollisionDetector {
  GamePanel gp;
  int leftCol;
  int rightCol;
  int upRow;
  int downRow;


  public CollisionDetector(GamePanel gp) {
    this.gp = gp;
  }

  public void checkTile(Entity entity) {
    int leftX = entity.worldX + entity.solidArea.x;
    int rightX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
    int upY = entity.worldY + entity.solidArea.y;
    int downY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

    leftCol = leftX / GamePanel.TILE_SIZE;
    rightCol = rightX / GamePanel.TILE_SIZE;
    upRow = upY / GamePanel.TILE_SIZE;
    downRow = downY / GamePanel.TILE_SIZE;

      //checking the player's direction
      if (entity.direction == UP) {
        upRow = (upY - entity.speed) / GamePanel.TILE_SIZE;
        collide(entity);
      } else if (entity.direction == DOWN) {
        downRow = (downY + entity.speed) / GamePanel.TILE_SIZE;
        collide(entity);
      } else if (entity.direction == LEFT) {
        leftCol = (leftX - entity.speed) / GamePanel.TILE_SIZE;
        collide(entity);
      } else if (entity.direction == RIGHT) {
        rightCol = (rightX + entity.speed) / GamePanel.TILE_SIZE;
        collide(entity);
      }
  }
  
  private void collide(Entity entity) {
    int tileNum1, tileNum2;
    tileNum1 = gp.tManager.map[leftCol][upRow];
    tileNum2 = gp.tManager.map[rightCol][downRow];
    
    if (gp.tManager.tiles[tileNum1].collision || gp.tManager.tiles[tileNum2].collision) {
      entity.collision = true;
    }
  }
  
  public int checkObject(Player p, boolean player) {
    int index = 999;
    for(int i = 0; i < gp.objects.length; i++) {
      if(gp.objects[i] != null) {
        //get entity/player's solid area position
        p.solidArea.x = p.worldX + p.solidArea.x;
        p.solidArea.y = p.worldY + p.solidArea.y;
        //get the object's solid area position
        gp.objects[i].solidArea.x = gp.objects[i].x + gp.objects[i].solidArea.x;
        gp.objects[i].solidArea.y = gp.objects[i].y + gp.objects[i].solidArea.y;

        if (p.direction == UP) {
          p.solidArea.y -= p.speed;
          if(p.solidArea.intersects(gp.objects[i].solidArea)){
            System.out.println("up collision");
            if(gp.objects[i].collision) {
              p.collision = true;
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
              p.collision = true;
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
              p.collision = true;
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
              p.collision = true;
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
