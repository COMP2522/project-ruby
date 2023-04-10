package org.project.Map;

import org.project.UI.GamePanel;

import java.util.ArrayList;
import static org.project.SystemVariables.*;

/**
 * The PathFinder class is responsible for finding the shortest path between two nodes in a grid.
 * It uses the A* algorithm to determine the path from the start node to the goal node.
 * The algorithm calculates the distance between nodes, and uses that to determine the shortest possible path.
 * The class contains helper methods to open nodes, check surrounding nodes, and track the path.
 *
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
  
  /**
   * Sets up nodes for calculations.
   * This method instantiates all the nodes in the game's map
   */
  public void instantiateNodes() {
    // Create a 2D array of nodes with dimensions MAP_COL by MAP_ROW
    nodes = new Node[MAP_COL][MAP_ROW];

    // Initialize row and column variables to 0
    int col = 0;
    int row = 0;

    // Iterate through each row and column of the 2D array and create a new Node object at each position
    while (col < MAP_COL && row < MAP_ROW) {

      // Create a new Node object at the current row and column position
      nodes[col][row] = new Node(col, row);

      // Move to the next column position
      col++;

      // If we have reached the end of the current row, move to the next row and reset the column position to 0
      if (col == MAP_COL) {
        col = 0;
        row++;
      }
    }
  }

  
  /** Reset nodes to default value. */
  public void resetNodes() {
    // Initialize row and column variables to 0
    int col = 0;
    int row = 0;

    // Iterate through each row and column of the 2D array of nodes
    while (col < MAP_COL && row < MAP_ROW) {
      // Reset the open, checked, and solid values for the current node to false
      nodes[col][row].open = false;
      nodes[col][row].checked = false;
      nodes[col][row].solid = false;

      // Move to the next column position
      col++;

      // If we have reached the end of the current row, move to the next row and reset the column position to 0
      if (col == MAP_COL) {
        col = 0;
        row++;
      }
    }

    // Clear the lists of open and path nodes
    openList.clear();
    pathList.clear();

    // Reset the goalReached flag and step counter to their default values
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
    // Reset all nodes in the map to their default values
    resetNodes();

    // Set the start, goal, and current nodes
    startNode = nodes[startCol][startRow];
    currentNode = startNode;
    goalNode = nodes[goalCol][goalRow];

    // Add the current node to the list of open nodes
    openList.add(currentNode);

    // Iterate through each row and column of the 2D array of nodes
    int col = 0;
    int row = 0;
    while (col < MAP_COL && row < MAP_ROW) {

      // Get the tile number for the current position
      int tileNum = gp.tManager.map[col][row];

      // If the tile is solid, mark the corresponding node as solid
      if (gp.tManager.tiles[tileNum].collision) {
        nodes[col][row].solid = true;
      }

      // Set the cost for the current node
      getCost(nodes[col][row]);

      // Move to the next column position
      col++;

      // If we have reached the end of the current row, move to the next row and reset the column position to 0
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

    // Calculate the g cost (the cost of moving from the start node to this node)
    int xDistance = Math.abs(node.col - startNode.col);
    int yDistance = Math.abs(node.row - startNode.row);
    node.gCost = xDistance + yDistance;

    // Calculate the h cost (the heuristic cost of moving from this node to the goal node)
    xDistance = Math.abs(node.col - goalNode.col);
    yDistance = Math.abs(node.row - goalNode.row);
    node.hCost = xDistance + yDistance;

    // Calculate the f cost (the total cost of moving from the start node to this node, then to the goal node)
    node.fCost = node.gCost + node.hCost;
  }

  /**
   * Searches for the shortest path from the start node to the goal node using the A* algorithm.
   *
   * @return true if the goal is reached, false otherwise
   */
  public boolean search() {
    // Continue searching until the goal is reached or the maximum number of steps is reached
    while (!goalReached && step < 500) {
      // Check the current node
      checkCurrentNode();

      // Check the surrounding nodes
      checkSurroundingNodes();

      // Find the node with the lowest fCost
      int bestNodeIndex = findBestNodeIndex();

      // If there is no node in the openList, end the loop
      if (bestNodeIndex == -1) {
        break;
      }

      // Set the currentNode to the best node
      currentNode = openList.get(bestNodeIndex);

      // If the currentNode is the goalNode, set goalReached to true and track the path
      if (currentNode == goalNode) {
        goalReached = true;
        trackThePath();
      }

      // Increment the step count
      step++;
    }

    // Return whether the goal was reached or not
    return goalReached;
  }

  /**
   * Marks the current node as checked and removes it from the openList.
   */
  private void checkCurrentNode() {
    currentNode.checked = true;
    openList.remove(currentNode);
  }

  /**
   * Checks the surrounding nodes and adds them to the openList if they meet the necessary conditions.
   */
  private void checkSurroundingNodes() {
    int col = currentNode.col;
    int row = currentNode.row;

    // Check the node above
    if (row - 1 >= 0) {
      openNode(nodes[col][row - 1]);
    }

    // Check the node to the left
    if (col - 1 >= 0) {
      openNode(nodes[col - 1][row]);
    }

    // Check the node below
    if (row + 1 < MAP_ROW) {
      openNode(nodes[col][row + 1]);
    }

    // Check the node to the right
    if (col + 1 < MAP_COL) {
      openNode(nodes[col + 1][row]);
    }
  }

  /**
   * Finds the node with the lowest fCost in the openList.
   *
   * @return the index of the best node in the openList, or -1 if the openList is empty
   */
  private int findBestNodeIndex() {
    int bestNodeIndex = -1;
    int bestNodefCost = MAX_INDEX;

    // Iterate through the openList to find the node with the lowest fCost
    for (int i = 0; i < openList.size(); i++) {
      Node node = openList.get(i);

      // If the node has a lower fCost than the current best node, update the best node
      if (node.fCost < bestNodefCost) {
        bestNodeIndex = i;
        bestNodefCost = node.fCost;

        // If the node has the same fCost as the current best node, use the gCost to break the tie
      } else if (node.fCost == bestNodefCost) {
        if (node.gCost < openList.get(bestNodeIndex).gCost) {
          bestNodeIndex = i;
        }
      }
    }

    // Return the index of the best node, or -1 if the openList is empty
    return bestNodeIndex;
  }

  /**
   * Adds the specified node to the open list if it is not solid, has not been checked before,
   * and has not already been added to the open list.
   *
   * @param node the node to be added to the open list
   */
  public void openNode(Node node) {
    if (!node.open && !node.checked && !node.solid) {
      node.open = true;
      node.parent = currentNode;
      openList.add(node);
    }
  }

  /**
   * Tracks the path from the start node to the goal node by following the chain of parent nodes
   * starting at the goal node and working backwards to the start node.
   * The resulting path is stored in the pathList in reverse order.
   */
  public void trackThePath() {
    Node current = goalNode;
    while (current != startNode) {
      pathList.add(0, current); // Add the node to the start of the list
      current = current.parent;
    }
  }
}
