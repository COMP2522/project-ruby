package org.project.Map;

import org.project.UI.GamePanel;

import java.util.ArrayList;
import static org.project.SystemVariables.*;

/**
 * Lets Monsters find the quickest path towards the player
 * @author Amrit Jhatu
 * @version 2023-04-04
 */
public class PathFinder {
  GamePanel gp;
  Node[][] nodes;
  ArrayList<Node> openList = new ArrayList<>();
  public ArrayList<Node> pathList = new ArrayList<>();

  Node startNode, goalNode, currentNode;
  boolean goalReached = false;
  int step = 0;
  
  /**
   * Constructs the new PathFinder object
   * @param gp GamePanel it applies to
   */
  public PathFinder(GamePanel gp) {
    this.gp = gp;
    instantiateNodes();
  }
  
  /** Sets up nodes for calculations. */
  public void instantiateNodes() {
    nodes = new Node[MAP_COL][MAP_ROW];
    int col = 0;
    int row = 0;
    while (col < MAP_COL && row < MAP_ROW) {
      nodes[col][row] = new Node(col, row);
      col++;
      if (col == MAP_COL) {
        col = 0;
        row++;
      }
    }
  }
  
  /** Reset nodes to default value. */
  public void resetNodes() {
    int col = 0;
    int row = 0;

    while (col < MAP_COL && row < MAP_ROW) {
      nodes[col][row].open = false;
      nodes[col][row].checked = false;
      nodes[col][row].solid = false;
      col++;
      if (col == MAP_COL) {
        col = 0;
        row++;
      }
    }
    
    openList.clear();
    pathList.clear();
    goalReached = false;
    step = 0;
  }
  
  /**
   * Set nodes to specific values.
   * @param startCol Current node column
   * @param startRow Current node row
   * @param goalCol Goal node column
   * @param goalRow Goal node row
   */
  public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
    resetNodes();
    startNode = nodes[startCol][startRow];
    currentNode = startNode;
    goalNode = nodes[goalCol][goalRow];
    openList.add(currentNode);

    int col = 0;
    int row = 0;
    while (col < MAP_COL && row < MAP_ROW) {
      int tileNum = gp.tManager.map[col][row];
      if (gp.tManager.tiles[tileNum].collision) {
        nodes[col][row].solid = true;
      }

      //Set Cost
      getCost(nodes[col][row]);
      col++;
        if (col == MAP_COL) {
            col = 0;
            row++;
        }
    }
  }
  
  /**
   * Get the A-Star cost
   * @param node Node to calculate
   */
  public void getCost(Node node) {
    // g cost
    int xDistance = Math.abs(node.col - startNode.col);
    int yDistance = Math.abs(node.row - startNode.row);
    node.gCost = xDistance + yDistance;
    // h cost
    xDistance = Math.abs(node.col - goalNode.col);
    yDistance = Math.abs(node.row - goalNode.row);
    node.hCost = xDistance + yDistance;
    // f cost
    node.fCost = node.gCost + node.hCost;
  }
  
  /**
   * Search for the goal node
   * @return Whether the goal has been reached or not
   */
  public boolean search(){
    while(!goalReached && step < 500) {
      int col = currentNode.col;
      int row = currentNode.row;
      // Check the current node
      currentNode.checked = true;
      openList.remove(currentNode);

      // Check the surrounding nodes.
      if (row- 1 >= 0) {
        openNode(nodes[col][row - 1]);
      }
      if(col - 1 >= 0){
        openNode(nodes[col - 1][row]);
      }
      if(row+1 < MAP_ROW){
        openNode(nodes[col][row+1]);
      }
      if (col+1 < MAP_COL) {
        openNode(nodes[col+1][row]);
      }
      // Find the node with the lowest fCost
      int bestNodeIndex = 0;
      int bestNodefCost = MAX_INDEX;

      for (int i = 0; i < openList.size(); i++){
        // Check if the node is better
        if(openList.get(i).fCost < bestNodefCost){
          bestNodeIndex = i;
          bestNodefCost = openList.get(i).fCost;
        } else if(openList.get(i).fCost == bestNodefCost){
          if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
              bestNodeIndex = i;
          }
        }
      }

      // If there is no node in the openList end the loop
      if(openList.size() == 0){
        break;
      }
      
      // After the loop set the currentNode to the best node
      currentNode = openList.get(bestNodeIndex);
      if(currentNode == goalNode){
        goalReached = true;
        trackThePath();
      }
      step++;
    }
    return goalReached;
  }
  
  /**
   * Add node to open list
   * @param node Node to be added
   */
  public void openNode(Node node){
    if(!node.open && !node.checked && !node.solid){
      node.open = true;
      node.parent = currentNode;
      openList.add(node);
    }
  }
  
  /** Tracks the path the node must take */
  public void trackThePath(){
    Node current = goalNode;
    while(current != startNode){
      pathList.add(0,current); // Add the node to the start of the list
      current = current.parent;
    }
  }
}
