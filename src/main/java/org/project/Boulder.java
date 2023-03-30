//package org.project;
//
//import javax.imageio.ImageIO;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//public class Boulder extends Projectile{
//
//  GamePanel gp;
//
//    public Boulder(GamePanel gp){
//        super(gp);
//        this.gp = gp;
//        speed = 5;
//
//        getImage();
//    }
//
//    public void getImage(){
//
//      try {
//        downR = ImageIO.read(new FileInputStream("assets/monsters/beast_down_right.png"));
//        downL = ImageIO.read(new FileInputStream("assets/monsters/beast_down_left.png"));
//        upR = ImageIO.read(new FileInputStream("assets/monsters/beast_up_right.png"));
//        upL = ImageIO.read(new FileInputStream("assets/monsters/beast_up_left.png"));
//        leftR = ImageIO.read(new FileInputStream("assets/monsters/beast_left_right.png"));
//        leftL = ImageIO.read(new FileInputStream("assets/monsters/beast_left_left.png"));
//        rightR = ImageIO.read(new FileInputStream("assets/monsters/beast_right_right.png"));
//        rightL = ImageIO.read(new FileInputStream("assets/monsters/beast_right_left.png"));
//      } catch (IOException e) {
//        System.out.println("Image can't be read ...");
//        e.printStackTrace();
//      }
//    }
//
//
//
//    public void update(){
//
//    }
//}
