package org.project;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Sound {
  Clip clip;
  private URL soundURL[] = new URL[30];

  public Sound() {
    try {
      soundURL[0] = new File("assets/sound/background.wav").toURI().toURL();
      soundURL[1] = new File("assets/sound/rubycollection.wav").toURI().toURL();
      soundURL[2] = new File("assets/sound/doorOpening.wav").toURI().toURL();
      soundURL[3] = new File("assets/sound/powerup.wav").toURI().toURL();
      soundURL[4] = new File("assets/sound/running.wav").toURI().toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    System.out.println(soundURL[0]);
  }

  public void setFile(int i) {
    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
      clip = AudioSystem.getClip();
      clip.open(ais);
      if (clip == null) {
        System.out.println("error");
      }
    } catch (Exception e) {
      System.out.println("unable to open file:  " + e + i);
    }
  }

  public void play() {
    clip.start();
  }

  public void loop() {
    clip.setFramePosition(0);
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void stop() {
    clip.stop();
  }
}
