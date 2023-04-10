# Project Ruby
<img width="771" alt="img1" src="https://user-images.githubusercontent.com/57815944/230837983-6182570e-b60a-4536-8f97-497583231af3.png">
<img width="765" alt="img2" src="https://user-images.githubusercontent.com/57815944/230838053-f700cd05-9705-4eca-9952-51cc05d635ea.png">
<img width="765" alt="Screen Shot 2023-04-09 at 8 55 28 PM" src="https://user-images.githubusercontent.com/57815944/230838209-37fd4152-a8a6-42b2-ab8d-adbc656e8361.png">

# Project Pitch
Ruby Rush is a open world game where players find themselves running from slimes and avoiding elements as they search for rubies.

# Instructions
1. Run Main.java
2. Follow the Menu interface, using mouse and keyboard inputs, to set a username that will be used to get your saved game, and begin.
3. Collect Rubies and don't get hit by monsters by using the WASD keys.
4. Game elements will teleport at random every couple of seconds.
5. Survive as long as you can.


# Requirements
**Requirement 1:** 
We used Java Graphics to draw all of our user interfaces, which has similar methods to Processing.org. Then it will be drawn in a JPanel.

**Requirement 2:** 
We used asynchronous processing to save the game each time each time the player encounters a ruby.

**Requirement 3:** 
The gamestate is stored as a JSON file, containing two key value pairs of gamePanelData and playerData. The value for gamePanelData is another JSONObject consisting of 3 arrays. 

**Requirement 4:**
We created a custom data structure based on a network of nodes to replace the int-map array. This structure stores references to all adjacent nodes (up, down, left, and right) of the current node, making it more efficient for accessing adjacent nodes. The focus of this structure is on efficiency.

**Requirement 5:** We have added javadoc, comments and provided a UML diagram.

# Contributions
Abhishek
- Player, Entity, UI, KeyHandler, UI, Life

Amrit
- Villager, Monster, Node, Pathfinder, ElementHandler testing

Nathan
- GamePanel, TileManager, Tile classes, refactoring, design

Simrat
- Objects package, CollisionDetector, Element, ElementHandler, sound testing 

Greg Song
- GameLauncher, SaveState, SaveStateHandler, Menu, MenuManager and related component classes


