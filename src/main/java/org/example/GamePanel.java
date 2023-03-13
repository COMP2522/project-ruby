package org.example;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

  final int originalTileSize = 16; // 16x16 tile
  final int scale = 3; // scale to 3 times to match screen resolution

  final int tileSize = originalTileSize * scale; // 48x48 tile

  final int maxScreenCol = 16;
  final int maxScreenRow = 12; // ratio is 4:3 for the whole window

  final int screenWidth = tileSize * maxScreenCol;  // 768 pixels wide
  final int screenHeight = tileSize * maxScreenRow;  // 576 pixels height
  //  int width;
  //  int height;
  int FPS = 60;

  Player player = new Player();

  Thread gameThread;

//  void loadMap() {}  // update map
//  void drawMap() {}  // draw map

  public void update() {
    if (player.upPressed == true) {
      player.y -= player.playerSpeed;
    } else if (player.downPressed == true) {
      player.y += player.playerSpeed;
    } else if (player.rightPressed == true) {
      player.x += player.playerSpeed;
    } else if(player.leftPressed == true) {
      player.x -= player.playerSpeed;
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.white);
    g2.fillRect(player.x, player.y, tileSize, tileSize);
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

  @Override
  public void run() {
    double drawInterval = 1000000000 / FPS;  // gives exactly how many nanoseconds should each frame last where FPS = 60?
    double nextDrawTime = System.nanoTime() + drawInterval; // this tells the system when to draw the next frame

    while(gameThread != null) {  // this is the game loop that will keep the game running, until the window is closed

      //      System.out.println("The game loop is running"); // test, will get replaced by actual content in future
      //1 UPDATE : update information such as charater positions
      //2 DRAW: draw the screen with the updated information
      update();
      repaint();

      try {
        double remainingTime = nextDrawTime - System.nanoTime(); // we need the system to hold the process or sleep for this time
        remainingTime /= 1000000; // convert nano time to milli time
        if (remainingTime < 0) {
          remainingTime = 0;
        }
        Thread.sleep((long) remainingTime);  // this method accepts milli seconds, not nanoseconds
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
