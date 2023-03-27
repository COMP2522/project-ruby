package org.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

/**
 * NPC_Villager is docile. Just randomly moves around.
 * This is the base file code independent of anything else.
 * Entity class will need to be made.
 * Sprite, GamePanel and SpriteManager will all require code
 * for this class to function as intended.
 * To display, update, collide and interact.
 *
 * @author Amrit Singh
 * @version 2023-02-07
 */
public class NPC_Villager extends Entity {


    public NPC_Villager(GamePanel gp) {
        super(gp);
        this.gp = gp;
        this.direction = directions.DOWN;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        this.x = 90;
        this.y = 50;
        this.speed = 4;
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

    /**
     * each class will have its own version of Pre-Determined action
     */
    @Override
    public void setAction(){

        actionLockCounter++;
        // Adjust the number within the if statement to control NPC's directional change interval based on frames.
        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // picks up a number from 1 to 100

            if(i <= 25) {
                direction = directions.UP;
            }
            if (i > 25 && i <= 50) {
                direction = directions.DOWN;
            }
            if(i > 50 && i <= 75) {
                direction = directions.LEFT;
            }
            if(i > 75 && i <= 100) {
                direction = directions.RIGHT;
            }
            // Reset the counter so when the next direction is selected
            // It will have to await the duration of the interval before changing again.
            actionLockCounter = 0;
        }
    }



}