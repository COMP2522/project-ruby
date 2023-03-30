//package org.project;
//
//public class Projectile extends Entity{
//
//  Entity user;
//
//
//  @Override
//  public void setAction() {
//
//  }
//
//  public void set (int worldX, int worldY, String direction, boolean alive, Entity user){
//    this.worldX = worldX;
//    this.worldY = worldY;
//    this.direction = directions.valueOf(direction);
//    this.alive = alive;
//    this.user = user;
//  }
//
//  public Projectile(GamePanel gp){
//    super(gp);
//  }
//
//  public void update(){
//    switch (direction){
//      case UP:
//        worldY -= speed;
//        break;
//      case DOWN:
//        worldY += speed;
//        break;
//      case LEFT:
//        worldX -= speed;
//        break;
//      case RIGHT:
//        worldX += speed;
//        break;
//    }
//
//    spriteCounter++;
//    if(spriteCounter > 12) {
//      if (spriteNum == 1) {
//        spriteNum = 2;
//      } else if (spriteNum == 2) {
//        spriteNum = 1;
//      }
//      spriteCounter = 0;
//    }
//
//  }
//}
