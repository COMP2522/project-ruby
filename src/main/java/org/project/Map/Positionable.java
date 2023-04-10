package org.project.Map;

/**
 * Interface to unite all entities and savable elements
 * @author Greg Song
 * @version 2023-04-04
 */
public interface Positionable {
  int getWorldX();
  int getWorldY();
  void setWorldX(int worldX);
  void setWorldY(int worldY);
}