package org.project.Entities;

import org.project.UI.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import static org.project.SystemVariables.*;

/**
 * Villager is docile. Just randomly moves around.
 * This is the base file code independent of anything else.
 * Entity class will need to be made.
 * Sprite, GamePanel and SpriteManager will all require code
 * for this class to function as intended.
 * To display, update, collide and interact.
 *
 * @author Amrit Singh
 * @version 2023-02-07
 */
public class Villager extends Entity {

    public Villager(GamePanel gp, int posX, int posY) {
        super(gp);
        this.gp = gp;
        hitbox = new Rectangle();
        hitbox.x = 8;
        hitbox.y = 8;
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        hitbox.width = 32;
        hitbox.height = 32;
        this.worldX = posX;
        this.worldY = posY;
        direction = directions.DOWN;
        speed = 2;
        getImage();
    }

    public void getImage() {
        try{
            downR = ImageIO.read(new FileInputStream("assets/player/oldman_down_right.png"));
            downL = ImageIO.read(new FileInputStream("assets/player/oldman_down_left.png"));
            upR = ImageIO.read(new FileInputStream("assets/player/oldman_up_right.png"));
            upL = ImageIO.read(new FileInputStream("assets/player/oldman_up_left.png"));
            leftR = ImageIO.read(new FileInputStream("assets/player/oldman_left_right.png"));
            leftL = ImageIO.read(new FileInputStream("assets/player/oldman_left_left.png"));
            rightR = ImageIO.read(new FileInputStream("assets/player/oldman_right_right.png"));
            rightL = ImageIO.read(new FileInputStream("assets/player/oldman_right_left.png"));
        } catch(IOException e) {
            System.out.println("Image can't be read ...");
            e.printStackTrace();
        }
    }
    
    @Override
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // picks up a number from 1 to 100
    
            switch (i / 25) {
                case 0 -> direction = directions.UP;
                case 1 -> direction = directions.DOWN;
                case 2 -> direction = directions.LEFT;
                case 3 -> direction = directions.RIGHT;
            }
            actionLockCounter = 0;
        }
    }
}