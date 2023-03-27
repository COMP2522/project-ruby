package org.project;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

  // GAME PANEL SETTINGS
  public final KeyHandler handler = new KeyHandler();
  public Player player = new Player(this, handler);
  
  // SCREEN SETTINGS
  public static final int TILE_SIZE = 48;
  public static final int SCREEN_COL = 18;
  public static final int SCREEN_ROW = 14;
  public final int screenWidth = TILE_SIZE * SCREEN_COL;  // 768 pixels wide
  public final int screenHeight = TILE_SIZE * SCREEN_ROW;  // 576 pixels height
  int FPS = 60;
  
  // WORLD MAP SETTINGS
  public static final int MAP_COL = 50;
  public static final int MAP_ROW = 50;
  public final int mapWidth = TILE_SIZE * MAP_COL;
  public final int mapHeight = TILE_SIZE * MAP_ROW;
  
  
  private int fireFrameUpdateCounter = 0;

  public AssetHandler assetHandler = new AssetHandler(this);
  public Object[] objects = new Object[10];

  public void setUpGame() {assetHandler.setObject();}
  public TileManager map1 = new TileManager(this); // this is actually like the manager of map
  public KeyHandler kh = new KeyHandler();

  Thread gameThread;
  

  public void update(){
    player.update(this, this.kh);
    updateFire();
  }

  public void updateFire() {
    fireFrameUpdateCounter++;
    int fireFrameUpdateInterval = 10;
    if (fireFrameUpdateCounter >= fireFrameUpdateInterval) {
      fireFrameUpdateCounter = 0;
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
    map1.draw(g2);
    //for objects
    for (Object object : objects) {
      if (object != null) {
        object.draw(g2, this);
      }
    }
    player.draw(g2);
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
    double drawInterval = 1000000000.0/FPS;
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