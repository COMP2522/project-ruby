package org.project;

import java.util.ArrayList;

public class PathFinder {
  GamePanel gp;
  Node[][] nodes;
  ArrayList<Node> openList = new ArrayList<>();
  public ArrayList<Node> pathList = new ArrayList<>();

  Node startNode, goalNode, currentNode;
  boolean goalReached = false;
  int step = 0;

  public PathFinder(GamePanel gp) {
    this.gp = gp;
    instantiateNodes();
//        nodes = new Node[GamePanel.MAP_COL][GamePanel.MAP_ROW];
//        for (int i = 0; i < GamePanel.MAP_COL; i++) {
//        for (int j = 0; j < GamePanel.MAP_ROW; j++) {
//            nodes[i][j] = new Node(i, j, gp.tileManager.tiles[gp.tileManager.map[i][j]].collision);
//        }
  }

  public void instantiateNodes() {
    nodes = new Node[GamePanel.MAP_COL][GamePanel.MAP_ROW];
    int col = 0;
    int row = 0;

    while (col < GamePanel.MAP_COL && row < GamePanel.MAP_ROW) {
      nodes[col][row] = new Node(col, row);
      col++;
      if (col == GamePanel.MAP_COL) {
        col = 0;
        row++;
      }
    }
  }

  public void resetNodes() {
    int col = 0;
    int row = 0;

    while (col < GamePanel.MAP_COL && row < GamePanel.MAP_ROW) {
      // Reset open with checked and splod state
      nodes[col][row].open = false;
      nodes[col][row].checked = false;
      nodes[col][row].solid = false;

      col++;
      if (col == GamePanel.MAP_COL) {
        col = 0;
        row++;
      }
    }
    // Reset the other settings
    openList.clear();
    pathList.clear();
    goalReached = false;
    step = 0;

  }

  public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
    resetNodes();
    // Set start and goal nodes
    startNode = nodes[startCol][startRow];
    currentNode = startNode;
    goalNode = nodes[goalCol][goalRow];
    openList.add(currentNode);

    int col = 0;
    int row = 0;

    while (col < GamePanel.MAP_COL && row < GamePanel.MAP_ROW) {
      // Set solid state
      // Check the tiles
      int tileNum = gp.tManager.map[col][row];
      if (gp.tManager.tiles[tileNum].collision) {
        nodes[col][row].solid = true;
      }
      // check interactive elements
//      for (int i = 0; i < gp.elements.length; i++) {
//        if (gp.elements[i] != null) {
//          int iCol = gp.tManager..worldX / GamePanel.TILE_SIZE;
//      }
//      }

      //Set Cost
      getCost(nodes[col][row]);
      col++;
        if (col == GamePanel.MAP_COL) {
            col = 0;
            row++;
        }
    }
  }

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
    public boolean search(){
      while(goalReached && step < 500) {
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
        if(row+1 < GamePanel.MAP_ROW){
          openNode(nodes[col][row+1]);
        }
        if (col+1 < GamePanel.MAP_COL) {
          openNode(nodes[col+1][row]);
        }

        // Find the node with the lowest fCost
        int bestNodeIndex = 0;
        int bestNodefCost = 999;

        for (int i = 0; i < openList.size(); i++){
          // Check if the node is better
          if(openList.get(i).fCost < bestNodefCost){
            bestNodeIndex = i;
            bestNodefCost = openList.get(i).fCost;
          }
          // Check if the node is the same, check gCost
          else if(openList.get(i).fCost == bestNodefCost){
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
    public void openNode(Node node){
      if(!node.open && !node.checked && !node.solid){
        node.open = true;
        node.parent = currentNode;
        openList.add(node);
      }
    }

    public void trackThePath(){
      Node current = goalNode;
      while(current != startNode){
        pathList.add(0,current); // Add the node to the start of the list
        current = current.parent;
      }
    }
}
