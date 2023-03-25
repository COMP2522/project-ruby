package org.sourceCode;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

  public final int originalTileSize = 16; // 16x16 tile
  public final int scale = 3; // scale to 3 times to match screen resolution

  public final int tileSize = originalTileSize * scale; // 48x48 tile

  public final int maxScreenCol = 16;
  public final int maxScreenRow = 12; // ratio is 4:3 for the whole window

  public final int screenWidth = tileSize * maxScreenCol;  // 768 pixels wide
  public final int screenHeight = tileSize * maxScreenRow;  // 576 pixels height
  //  int width;
  //  int height;
  int FPS = 60;

  public int getScreenWidth() {
    return screenWidth;
  }

  public int getScreenHeight() {
    return screenHeight;
  }

  Player player = new Player();

  public Map map1 = new Map(this); // this is actually like the manager of map

  Thread gameThread;


//  void loadMap() {}  // update map
//  void drawMap() {}  // draw map

  public void update() {
    player.updatePosition(this);
  }

  //instantiating the collision checker class here.
  public Collision_Detection cChecker = new Collision_Detection(this);

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    map1.draw(g2, this);
    player.draw(g2, this);
    g2.dispose();
  }

  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(player);
    this.setFocusable(true);
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  // sleep method to set FPS
//  @Override
//  public void run() {
//    double drawInterval = 1000000000 / FPS;  // gives exactly how many nanoseconds should each frame last where FPS = 60?
//    double nextDrawTime = System.nanoTime() + drawInterval; // this tells the system when to draw the next frame
//
//    while(gameThread != null) {  // this is the game loop that will keep the game running, until the window is closed
//
//      //      System.out.println("The game loop is running"); // test, will get replaced by actual content in future
//      //1 UPDATE : update information such as character positions
//      //2 DRAW: draw the screen with the updated information
//      update();
//      repaint();
//
//      try {
//        double remainingTime = nextDrawTime - System.nanoTime(); // we need the system to hold the process or sleep for this time
//        remainingTime /= 1000000; // convert nano time to milli time
//        if (remainingTime < 0) {
//          remainingTime = 0;
//        }
//        Thread.sleep((long) remainingTime);  // this method accepts milliseconds, not nanoseconds
//        nextDrawTime += drawInterval;
//      } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//      }
//    }
//  }

  // second implementation to set FPS, better as far as I know
  @Override
  public void run() {
    double drawInterval = 1000000000/FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int drawCount = 0;

    while(gameThread != null) {
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / drawInterval;
      timer += (currentTime - lastTime);
      lastTime = currentTime;

      if (delta >= 1) {
        update();
        repaint();
        delta--;
        drawCount++;
      }
      if (timer >= 1000000000) {
//        System.out.println("FPS: " + drawCount);  // shows FPS
        drawCount = 0;
        timer = 0;
      }
    }
  }
}
