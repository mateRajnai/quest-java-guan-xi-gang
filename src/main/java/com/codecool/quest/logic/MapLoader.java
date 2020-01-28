package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.items.Hammer;
import com.codecool.quest.logic.items.Key;
import com.codecool.quest.logic.actors.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    private static int currentLevel;
    private static final int NUMBER_OF_LEVELS = 3;

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static boolean hasNextLevel() {
        return currentLevel < NUMBER_OF_LEVELS;
    }

    public static GameMap loadMap() {
        return loadMap(++currentLevel);
    }

    public static GameMap loadMap(int level) {
        InputStream is = MapLoader.class.getResourceAsStream(String.format("/map%d.txt", level));
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine();
        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'h':
                            cell.setType(CellType.FLOOR);
                            new Hammer(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            Player player;
                            if (currentLevel == 1) {
                                player = new Player(cell);
                            } else {
                                player = Player.getInstance();
                                player.setCell(cell);
                            }
                            map.setPlayer(player);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            new Bat(cell);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            new Golem(cell);
                            break;
                        case 'd':
                            cell.setType(CellType.FLOOR);
                            new Duck(cell);
                            break;
                        case 'u':
                            cell.setType(CellType.UPSTAIRS);
                            break;
                        case '\\':
                            cell.setType(CellType.DOWNSTAIRS);
                            break;
                        case 'c':
                            cell.setType(CellType.CAMPFIRE);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new Pot(cell);
                            break;
                        case 't':
                            cell.setType(CellType.BRONZE_TORCH);
                            break;
                        case 'r':
                            cell.setType(CellType.DRIED_BRANCH);
                            break;
                        case 'o':
                            cell.setType(CellType.STONES);
                            break;
                        case 'a':
                            cell.setType(CellType.GRASS2);
                            break;
                        case 'e':
                            cell.setType(CellType.CHEST_CLOSED);
                            new Chest(cell);
                            break;
                        case 'x':
                            cell.setType(CellType.DOOR_CLOSED);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
