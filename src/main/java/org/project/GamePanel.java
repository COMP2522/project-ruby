package org.project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Manages every other manager and displays the game on screen
 *
 * @author Nathan Bartyuk
 * @version 2023-03-28
 */
public class GamePanel extends JPanel implements Runnable {

  // GAME PANEL SETTINGS
  public final KeyHandler handler = new KeyHandler();
  public Player player = Player.getInstance(this, handler);
  
  // SCREEN SETTINGS
  public static final int TILE_SIZE = 48;
  public static final int SCREEN_COL = 16;
  public static final int SCREEN_ROW = 12;
  public final int screenWidth = TILE_SIZE * SCREEN_COL;  // 768 pixels wide
  public final int screenHeight = TILE_SIZE * SCREEN_ROW;  // 576 pixels height
  public final double drawInterval = 1000000000.0 / 60;
  
  // WORLD MAP SETTINGS
  public static final int MAP_COL = 50;
  public static final int MAP_ROW = 50;
  public final int mapWidth = TILE_SIZE * MAP_COL;
  public final int mapHeight = TILE_SIZE * MAP_ROW;
  
  
  public TileManager tManager = new TileManager(this); // this is actually like the manager of map
  public CollisionDetector cDetector = new CollisionDetector(this);
  public KeyHandler kHandler = new KeyHandler();
  public Thread gameThread;
  public Element[] elements = new Element[20];

  public Entity[] npc = new Entity[10];
  public Entity[] monster = new Entity[10];
  public ArrayList<Entity> projectileList = new ArrayList<>();
  public ElementHandler aHandler = new ElementHandler(this);
  
  /** Constructor for the GamePanel */
  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(kHandler);
    this.setFocusable(true);
  }
  
  /** Instantiates the game upon launch */
  public void setUpGame() {
    aHandler.setElement();
    aHandler.setNPC();
    aHandler.setMonster();
  }
  
  /** Updates the player, npc, and monster positions */
  public void update(){

    player.update(this, this.kHandler);

    // NPC
    for (Entity entity : npc) {
      if (entity != null) {
        // Checks if it is null, if not then it calls for update.
        entity.update();
      }
    }
    // MONSTER
    for (Entity entity : monster) {
      if (entity != null) {
        entity.update();
      }
    }
  }
  
  /**
   * Draws every component on the screen.
   * @param g the Graphics object to protect
   */
  @Override
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
    for (Entity entity : npc) {
      if (entity != null) {
        entity.draw(g2);
      }
    }

    // MONSTER
    for (Entity entity : monster) {
      if (entity != null) {
        entity.draw(g2);
      }
    }
    
    // DRAW PLAYER
    player.draw(g2);
    g2.dispose();
  }
  
  /** Starts the game thread */
  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }
  
  /** Runs the game thread */
  @Override
  public void run() {
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
      if (timer >= drawInterval) {
        timer = 0;
      }
    }
  }
}
