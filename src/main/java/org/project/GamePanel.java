package org.project;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

  // GAME PANEL SETTINGS
  public final KeyHandler handler = new KeyHandler();
  public Player player = new Player(this, handler);
  
  // SCREEN SETTINGS
  public static final int TILE_SIZE = 48;
  public static final int SCREEN_COL = 16;
  public static final int SCREEN_ROW = 12;
  public final int screenWidth = TILE_SIZE * SCREEN_COL;  // 768 pixels wide
  public final int screenHeight = TILE_SIZE * SCREEN_ROW;  // 576 pixels height
  int FPS = 60;
  
  // WORLD MAP SETTINGS
  public static final int MAP_COL = 50;
  public static final int MAP_ROW = 50;
  public final int mapWidth = TILE_SIZE * MAP_COL;
  public final int mapHeight = TILE_SIZE * MAP_ROW;
  
  
  public TileManager tManager = new TileManager(this); // this is actually like the manager of map
  public CollisionDetector cDetector = new CollisionDetector(this);
  public KeyHandler kHandler = new KeyHandler();
  public Thread gameThread;
  public Element[] elements = new Element[10];

  public Entity npc[] = new Entity[10];
  public Entity monster[] = new Entity[10];
  public ElementHandler aHandler = new ElementHandler(this);
  
  
  public void setUpGame() {
    aHandler.setElement();
    aHandler.setNPC();
    aHandler.setMonster();
  }

  public void update(){
    player.update(this, this.kHandler);

    // NPC
    for(int i = 0; i < npc.length; i++) {
      if (npc[i] != null) {
        // Checks if it is null, if not then it calls for update.
        npc[i].update();
      }
    }
    // MONSTER
    for(int i = 0; i < monster.length; i++) {
      if (monster[i] != null) {
        monster[i].update();
      }
    }


  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    
    // DRAW TILES
    tManager.draw(g2);
    
    // DRAW Elements
    for (Element element : elements) {
      if (element != null) {
        element.draw(g2,this);
      }
    }

    //NPC
    for(int i = 0; i < npc.length; i++) {
      if (npc[i] != null) {
        npc[i].draw(g2);
      }
    }

    // MONSTER
    for(int i = 0; i < monster.length; i++) {
      if (monster[i] != null) {
        monster[i].draw(g2);
      }
    }

    
    // DRAW PLAYER
    player.draw(g2);
    g2.dispose();
  }


  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(kHandler);
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

    while(gameThread != null) {
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / drawInterval;
      timer += (currentTime - lastTime);
      lastTime = currentTime;

      if (delta >= 1) {
        update();
        repaint();
        delta--;
      }
      if (timer >= 1000000000) {
        timer = 0;
      }
    }
  }
}
