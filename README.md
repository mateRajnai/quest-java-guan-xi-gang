# Codecool Quest

This is a simple tile-based RPG game.

## Opening the project

Open the project in IntelliJ IDEA. This is a Maven project, so you will need to open `pom.xml`.

The project is using JavaFX, and should work with Oracle's Java 8.

## Architecture

The project is meant to teach the concept of **layer separation**. All of the game logic (that is, player movement, game rules, and so on), is in the `logic` package, completely independent of user interface code. In principle, you could implement a completely different interface (terminal, web, Virtual Reality...) for the same logic code.

Thanks to the separation, we are also able to easily write **unit tests** for the logic.

## Implementation

Take a look at `user-stories.md` for a list of proposed user stories.

## Graphics

The tiles used in the game are from [1-Bit Pack by Kenney](https://kenney.nl/assets/bit-pack), shared on [CC0 1.0 Universal license](https://creativecommons.org/publicdomain/zero/1.0/).

![tiles](src/main/resources/tiles.png)