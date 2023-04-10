package org.project.Map;

/**

 This class represents a node used in the Path Finder algorithm.

 It contains pointers to adjacent nodes and various variables for calculation.

 @author Amrit Jhatu

 @version 2023-04-04
 */
public class Node {
  // A reference to the parent node
  public Node parent;

  // References to adjacent nodes
  public Node up;
  public Node down;
  public Node left;
  public Node right;

  // Variables for calculation
  public int col; // Column index of the node
  public int row; // Row index of the node
  protected int gCost; // Cost from the starting node to this node
  protected int hCost; // Heuristic cost from this node to the goal node
  protected int fCost; // Total cost (gCost + hCost) of reaching the goal node through this node
  protected boolean solid; // Indicates whether this node is an obstacle or not
  protected boolean open; // Indicates whether this node is open or not (i.e., not yet visited)
  protected boolean checked; // Indicates whether this node has been checked during the search process or not

  /**

   Creates a new Node object with the given column and row indices.
   @param col The column index of the node.
   @param row The row index of the node.
   */
  public Node(int col, int row) {
    this.col = col;
    this.row = row;
  }
}
