package org.project.UI;

import org.project.Entities.Entity;
import org.project.Entities.KeyHandler;
import org.project.Entities.Player;
import org.project.Map.PathFinder;
import org.project.Map.TileManager;
import org.project.Objects.CollisionDetector;
import org.project.Objects.Element;
import org.project.Objects.ElementHandler;

import javax.swing.*;
import java.awt.*;
import static org.project.Objects.CollisionDetector.*;
import static org.project.SystemVariables.*;

/**
 * The GamePanel class represents the main panel of the game. It extends JPanel and implements Runnable.
 * This class contains the settings for the screen and manages the different managers, key handlers, entities,
 * and UI of the game. It also contains methods to update and draw the game and start the game thread.
 *
 * @author Nathan Bartyuk, and Others
 * @version April 8, 2023
 */
public class GamePanel extends JPanel implements Runnable {
  
  // SCREEN SETTINGS
  public final int screenWidth = TILE_SIZE * SCREEN_COL;
  public final int screenHeight = TILE_SIZE * SCREEN_ROW;
  public final int mapWidth = TILE_SIZE * MAP_COL;
  public final int mapHeight = TILE_SIZE * MAP_ROW;
  
  // INSTANTIATES OTHER MANAGERS
  // These we believe should be stored here in the master-kind of class
  // and be accessible to all components through the game panel
  // these managers still manage their own selves, and enforce encapsulation, just putting them here, means
  // these instances are accesible to all components through GamePanel, which in turn is made accessible explicitly
  public static GamePanel instance;
  public TileManager tManager = new TileManager(this);
  public CollisionDetector cDetector = getCollisionDetector(this);
  public KeyHandler kHandler = new KeyHandler();
  public Player player = Player.getInstance(this, kHandler);
  public UI ui = new UI(this);
  public ElementHandler aHandler = new ElementHandler(this);
  public PathFinder pFinder = new PathFinder(this);
  public Sound sound = new Sound();
  public Sound soundEffect = new Sound();
  public Thread gameThread;
  public Element[] elements = new Element[20];
  public Entity[] npc = new Entity[10];
  public Entity[] monster = new Entity[10];

  /**
   * Creates a new GamePanel object with the specified dimensions and default background color.
   * This constructor sets the preferred size of the panel to the specified dimensions, sets the
   * background color to black, enables double buffering, and adds a key listener to the panel.
   * Additionally, it sets the panel to be focusable.
   */
  private GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(kHandler);
    this.setFocusable(true);
  }

  /**
   * Returns the GamePanel instance, creating it if it doesn't exist.
   * This method creates an instance of the GamePanel class if one does not already exist, and returns it.
   *
   * @return the GamePanel instance
   */
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

  /**
   * Updates the positions of the player, NPCs, and monsters in the game.
   * This method updates the position of the player and calls the update() method
   * of each NPC and monster in the game.
   */
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

  /**
   * method to draw all components in the Game-J-Panel.
   * Calls the draw methods of all entities (NPCs, player, Monster)
   *    elements, objects and UI in a single place as the master method.
   * @param g the <code>Graphics</code> object attached to JPanel which helps in drawing
   */
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

  /**
   * Runs the game thread.
   * This method initializes variables used in the game loop, calculates the elapsed time since the last frame,
   * and updates and redraws the game at a fixed frame rate.
   */
  @Override
  public void run() {
    // Initialize variables
    double delta = 0; // The amount of time passed since the last frame
    long lastTime = System.nanoTime(); // The time at the start of the loop
    long currentTime; // The current time
    long timer = 0; // Keeps track of how long the loop has been running

    // Loop until gameThread is null
    while(gameThread != null) {
      // Get the current time and calculate delta
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / DRAW_INTERVAL;

      // Add the time elapsed since the last frame to the timer
      timer += (currentTime - lastTime);

      // Set lastTime to the current time
      lastTime = currentTime;

      // If enough time has passed to draw the next frame
      if (delta >= 1) {
        // Update the game state
        update();

        // Draw the game again, this is weird but this actually calls the paintComponent method again
        // method from the java package (SWING library)
        repaint();

        // Decrement delta by 1 to account for the drawn frame
        delta--;
      }

      // If the loop has been running for longer than DRAW_INTERVAL
      if (timer >= DRAW_INTERVAL) {
        // Reset the timer
        timer = 0;
      }
    }
  }

  /**
   * Plays a music file specified by the given index.
   *
   * @param i the index of the music file to play
   */
  public void playMusic(int i) {
    sound.setFile(i);
    sound.play();
    sound.loop();
  }

  /**
   * Plays a sound effect specified by the given index.
   *
   * @param i the index of the sound effect to play
   */
  public void playSE(int i) {
    soundEffect.setFile(i);
    soundEffect.play();
  }

}
