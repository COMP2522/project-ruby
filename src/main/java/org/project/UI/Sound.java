package org.project.UI;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Sound {

  /** int codes for Sounds being used in this project.*/
  public static final int backgroundMusic = 0;
  public static final int rubyGetSound = 1;
  public static final int doorOpenSound = 2;
  public static final int powerUpSound = 3;
  public static final int runningSound = 4;

  /** Clip object from the java package through which sound is being output in AudioStream.*/
  public Clip clip;

  /** stores the file in-system URLs of all sound files to be set to clip whenever
   * a particular sound is to be played.
   */
  private final URL[] soundURL = new URL[30];

  /**
   * constructor to initialize the sound object's array with URL
   * of all sound files to be run in the game.
   */
  public Sound() {
    try {
      soundURL[backgroundMusic] = new File("assets/sound/background.wav").toURI().toURL();
      soundURL[rubyGetSound] = new File("assets/sound/rubycollection.wav").toURI().toURL();
      soundURL[doorOpenSound] = new File("assets/sound/doorOpening.wav").toURI().toURL();
      soundURL[powerUpSound] = new File("assets/sound/powerup.wav").toURI().toURL();
      soundURL[runningSound] = new File("assets/sound/running.wav").toURI().toURL();
    } catch (MalformedURLException e) {
      System.err.println("Unable to get sound files");
    }
  }

  /**
   * function to setup the clip and which sound to be played
   * @param i the int code of the file to be used (defined as static variables in class above)
   */
  public void setFile(int i) {
    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
      clip = AudioSystem.getClip();
      clip.open(ais);
      if (clip == null) {
        System.err.println("Unable to setup sound files");
      }
    } catch (Exception e) {
      System.err.println("unable to open file:  " + e + i);
    }
  }

  /**
   * method to play the sound.
   * Calls the java library start method on the Sound Clip object.
   */
  public void play() {
    clip.start();
  }

  /**
   * method to loop a sound clip continuosly.
   * Being used to loop the BGM music.
   */
  public void loop() {
    clip.setFramePosition(0);
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  /**
   * method to stop a clip or stop sound.
   */
  public void stop() {
    clip.stop();
  }
}
