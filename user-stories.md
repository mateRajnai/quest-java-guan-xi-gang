# User stories

Here is a list of user story. Each user story has:
- a title 
- short description
- acceptance criteria (as the bullet list)
- some implementation hints

In addition, there are some requirements for **all** user stories:
- **Tests**: Try to add at least one test for each feature. If a feature changes game behavior, it can be tested! (You don't need to test the user interface part, only the logic.)
- **Layer separation**: All game logic (character movement, and so on) has to be in the `logic` package. The `logic` package should not depend on your UI code.

The features have different priorities:
- **A: Mandatory**. Try to implement all. Other features depend on them.
- **B: Important**. Try to implement at least 2-3. They teach you various programming techniques.
- **C: Nice to have**. Not that important, but might be fun. Feel free to come up with your own nice-to-have ideas!

## Basic program (A)

*This should be already in your starting code.*

As a player, I want to explore a dungeon, and meet some friendly skeletons.

- Load map from file (`map.txt`)
- Display map
- Display player and monsters
- Move around using arrow keys
- Display player health

## Restrict movement (A)

I want not to be able to move into walls, and into monsters.

- Trying to move into walls should have no effect
- Trying to move into a monster should have no effect

**Implementation**: There are some tests related to that. Try to make them pass. Write more tests (for instance for moving into monster).

## Items (A)

I want to see items in the dungeon.

- There are at least 2 item types: for instance a key, and a sword
- There can be one item in a map square
- The item has to be displayed on screen (unless somebody stands on the same square).

## Pick up items (B)

I want to be able to pick up an item.

- There is a "Pick up" button on the right side of the screen.
- There is an "Inventory" list on the screen.
- After I click the button, the item I'm standing on should be gone from map, and should appear in my inventory.

**Implementation**: For displaying inventory, take a look at JavaFX's ListView class.

## Attack monsters (B)

I want to be able to attack monsters by moving into them.

- Attacking a monster removes a random amount of health (between 1 and 10). If health of a monster goes below 0, it dies and disappears.
- If you attack a monster, and it doesn't die, it also attacks you at the same time (but is a bit weaker, and only removes between 1 and 3 health).
- If you implemented inventory, then having sword can increase your attack strength.

## Doors (B)

I want to encounter doors, and open them using keys.

- There are two new square types: closed door, and open door.
- You cannot pass through a closed door, unless you have a Key in your inventory. Then it becomes an open door.

## Monsters move randomly (B)

I want the monsters to also move around, so that the game is more interesting.

- The monsters should make a move in random direction, every time the player makes a move.
- The monsters shouldn't collide with walls (same as player). But if you want to, they can attack the player!

## More monster types (C)

I want to see more different monster types. They should have different behavior.

- Including the skeleton, there are at least 3 different monsters with different graphics.
- They have different behavior:
  * One type doesn't move at all.
  * One type moves randomly.
  * (You can add more exciting behavior, like moving towards player, or teleportation to random spots).
  
**Implementation**: It's tempting to create implementations in different monster classes. But what if 2 monsters have the same behavior? Maybe you can create a separate `Behavior` class? This is called a [Strategy pattern](https://www.baeldung.com/java-strategy-pattern).

## More features (C)

As a player, I want to encounter more varied scenery.

- Take a look at the tile sheet (`tiles.png`). Get inspired!
- Use at least 3 more tiles. These can be more monsters, items, or background.
- At least one of them has to be not purely decorative, but have some effect on gameplay.

## Character name (C)

As a player, I want to set a name for my character. This name will also function as a cheat code!

- There is a `Name:` label and text field on the screen.
- Cheat code: If the name is *(one of the game developers)*, the player can walk through walls!

**Implementation**: Don't forget that the game logic has to be in `Player` class, so you need to update the name after editing.

## Save and load (C)

As a player, I want to save the game to a file, and load it from a file.

- There is a "Save" button that will ask for a file, and save the game to a file.
- The "Load" button will load the game from a file.
- The file format doesn't matter, but JSON is a good choice, because you can take a look from outside.

**Implementation**: Saving and loading objects is called serialization / deserialization. You can for instance use [Java serialization](https://www.baeldung.com/java-serialization) or [Jackson](https://www.baeldung.com/jackson-object-mapper-tutorial).