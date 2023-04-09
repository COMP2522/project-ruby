package org.project;

public abstract class Component {
  private int x;
  private int y;
  private int width;
  private int height;

  public Component(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public int getWidth() {return this.width;}
  public int getHeight() {return this.height;}
  public int getX() {return this.x;}
  public int getY() {return this.y;}
}
