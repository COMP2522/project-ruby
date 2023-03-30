package org.project;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Sound {
  Clip clip;
  URL soundURL[] = new URL[30];

  public Sound() {
    try {
      soundURL[0] = new File("assets/sound/fire2.wav").toURI().toURL();
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
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void stop() {
    clip.stop();
  }
}
