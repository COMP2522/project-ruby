package org.sourceCode;

import java.awt.*;
import javax.swing.JPanel;

/**
 * Defines the "Window" or the screen where all the elements of the game are drawn and outputted.
 *
 * @author Abhishek Chouhan
 * @version 2023-02-04
 */
public class Window extends JPanel implements Runnable{

  final int originalTileSize = 16; // 16x16 tile
  final int scale = 3; // scale to 3 times to match screen resolution

  final int tileSize = originalTileSize * scale; // 48x48 tile

  final int maxScreenCol = 16;
  final int maxScreenRow = 12; // ratio is 4:3 for the whole window

  final int screenWidth = tileSize * maxScreenCol;  // 768 pixels wide
  final int screenHeight = tileSize * maxScreenRow;  // 576 pixels height
  //  int width;
  //  int height;

  Thread gameThread;

  void loadMap() {}  // update map
  void drawMap() {}  // draw map

  public void update() {

  }

  public void paintComponent(Graphics g) {

  }

  public Window() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    while(gameThread != null) {  // this is the game loop that will keep the game running, until the window is closed
//      System.out.println("The game loop is running"); // test, will get replaced by actual content in future
      //1 UPDATE : update information such as charater positions
      //2 DRAW: draw the screen with the updated information
    }
  }
}
