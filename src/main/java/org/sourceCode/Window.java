package org.sourceCode;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;



/**
 * Defines the "Window" or the screen where all the elements of the game are drawn and outputted.
 *
 * @author Abhishek Chouhan
 * @version 2023-02-04
 */
public class Window extends PApplet implements Runnable {

  final int originalTileSize = 16; // 16x16 tile
  final int scale = 3; // scale to 3 times to match screen resolution
  final int tileSize = originalTileSize * scale; // 48x48 tile

  final int maxScreenCol = 16;
  final int maxScreenRow = 12; // ratio is 4:3 for the whole window

  final int screenWidth = tileSize * maxScreenCol;
  final int screenHeight = tileSize * maxScreenRow;
  
  Thread gameThread;
  int FPS = 60;
  Player player = new Player();
  Map map = new Map(this);
  

  public Window() {
    this.size(screenWidth,screenHeight, PConstants.JAVA2D);
    this.background(0);
    this.noStroke();
    this.frameRate(FPS);
  }
  
  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }
  
  public void draw() {
    PGraphics g = this.createGraphics(screenWidth,screenHeight,PConstants.JAVA2D);
    map.draw(g,this);
    player.draw(g,this);
    this.image(g,0,0);
    g.dispose();
  }

  public void update() {
    player.updatePosition();
  }
  
  @Override
  public void run() {
    while (gameThread != null) {
      update();
      draw();
    }
  }
  
  public void keyPressed() {
    player.move(this.keyCode);
  }
  
  public void keyReleased() {
    player.stop(this.keyCode);
  }
}
