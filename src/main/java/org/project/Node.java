package org.project;

/**
 * Node created to implement the Path Finder algorithm
 * @author Amrit Jhatu
 * @version 2023-04-04
 */
public class Node {
  public Node parent;
  public int col;
  public int row;
  protected int gCost;
  protected int hCost;
  protected int fCost;
  protected boolean solid;
  protected boolean open;
  protected boolean checked;

  public Node(int col, int row) {
    this.col = col;
    this.row = row;
  }
}
