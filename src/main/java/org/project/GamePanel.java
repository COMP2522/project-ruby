package org.project;

import org.project.Entities.Entity;
import org.project.Entities.Player;
import org.project.Objects.CollisionDetector;
import org.project.Objects.Element;
import org.project.Objects.ElementHandler;

import javax.swing.*;
import java.awt.*;
import static org.project.Objects.CollisionDetector.*;
import static org.project.SystemVariables.*;

/**
 * Manages every other manager and displays the game on screen
 *
 * @author Nathan Bartyuk
 * @version 2023-03-28
 */
public class GamePanel extends JPanel implements Runnable {
  
  // SCREEN SETTINGS
  public final int screenWidth = TILE_SIZE * SCREEN_COL;
  public final int screenHeight = TILE_SIZE * SCREEN_ROW;
  public final int mapWidth = TILE_SIZE * MAP_COL;
  public final int mapHeight = TILE_SIZE * MAP_ROW;
  
  // INSTANTIATES OTHER MANAGERS
  public static GamePanel instance;
  public TileManager tManager = new TileManager(this);
  public CollisionDetector cDetector = getCollisionDetector(this);
  public KeyHandler kHandler = new KeyHandler();
  public Player player = Player.getInstance(this, kHandler);
  public UI ui = new UI(this);
  public Sound sound = new Sound();
  public Sound soundEffect = new Sound();
  public Thread gameThread;
  public Element[] elements = new Element[20];
  public Entity[] npc = new Entity[10];
  public Entity[] monster = new Entity[10];
  public ElementHandler aHandler = new ElementHandler(this);
  public PathFinder pFinder = new PathFinder(this);
  

  /** Constructor for the GamePanel */
  private GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(kHandler);
    this.setFocusable(true);
  }
  
  public static GamePanel getGamePanel() {
    if (instance == null) {
      instance = new GamePanel();
    }
    return instance;
  }
  
  /** Instantiates the game upon launch */
  public void setUpGame() {
    aHandler.setElement();
    aHandler.setNPC();
    aHandler.setMonster();
    playMusic(0);
  }
  
  /** Updates the player, npc, and monster positions */
  public void update(){

    player.update(this, this.kHandler);

    // NPC
    for (Entity entity : npc) {
      if (entity != null) {
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
  
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    tManager.draw(g2);
    
    // Draw Elements - OBJECTS
    for (Element element : elements) {
      if (element != null) {
        element.draw(g2,this);
      }
    }
    // Draw docile characters - NPCs
    for (Entity entity : npc) {
      if (entity != null) {
        entity.draw(g2);
      }
    }
    // Draw hostile characters - MONSTERS
    for (Entity entity : monster) {
      if (entity != null) {
        entity.draw(g2);
      }
    }
    
    // DRAW PLAYER
    player.draw(g2);
    ui.draw(g2);
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
      delta += (currentTime - lastTime) / DRAW_INTERVAL;
      timer += (currentTime - lastTime);
      lastTime = currentTime;

      if (delta >= 1) {
        update();
        repaint();
        delta--;
      }
      if (timer >= DRAW_INTERVAL) {
        timer = 0;
      }
    }
  }
  public void playMusic(int i) {
    sound.setFile(i);
    sound.play();
    sound.loop();
  }

  public void playSE(int i) {
    soundEffect.setFile(i);
    soundEffect.play();
  }
}
