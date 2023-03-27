package org.sourceCode;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

  // In the GamePanel class
  public final KeyHandler handler = new KeyHandler();
  public Player player = new Player(this, handler);

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

  // Add a variable to control the fire animation frame update interval
  private int fireAnimationFrameUpdateInterval = 10;
  private int fireAnimationFrameUpdateCounter = 0;

  public Asset_Handler assetHandler = new Asset_Handler(this);
  public Object[] objects = new Object[10];

  public void setUpGame() {
    assetHandler.setObject();
  }

  public int getScreenWidth() {
    return screenWidth;
  }

  public int getScreenHeight() {
    return screenHeight;
  }

//  Player player = new Player();

  public Map map1 = new Map(this); // this is actually like the manager of map

  public KeyHandler kh = new KeyHandler();

  Thread gameThread;


//  void loadMap() {}  // update map
//  void drawMap() {}  // draw map

  public void update(){
    player.update(this, this.kh);
    updateFireAnimationFrames();
  }

  public void updateFireAnimationFrames() {
    fireAnimationFrameUpdateCounter++;
    if (fireAnimationFrameUpdateCounter >= fireAnimationFrameUpdateInterval) {
      fireAnimationFrameUpdateCounter = 0;
      for (Object object : objects) {
        if (object != null && object.name != null && object.name.equals("fire")) {
          object.currentFrame = (object.currentFrame + 1) % 4;
        }
      }
    }
  }

  //instantiating the collision checker class here.
  public CollisionDetector cChecker = new CollisionDetector(this);

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    map1.draw(g2, this);
    //for objects
    for (Object object : objects) {
      if (object != null) {
        object.draw(g2, this);
      }
    }
    player.draw(g2, this);
    g2.dispose();
  }

  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(kh);
    this.setFocusable(true);
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

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
