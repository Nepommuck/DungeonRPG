package game;

import game.enemies.Bat;
import game.enemies.Enemy;

import static game.MapElement.*;

public class MapElementParser {
    public static MapElement parse(char character) {
        MapElement result = switch (character) {
            case '.' -> EMPTY;
            case '#' -> WALL;
            case 'P' -> PLAYER;
            case 'B' -> ENEMY;
            default -> null;
        };
        if (result == null)
            throw new IllegalArgumentException("Illegal character \'" + character + "\' in map file");
        return result;
    }

    public static Enemy createEnemy(char character, Vector2d position) {
        Enemy enemy = switch (character) {
            case 'B' -> new Bat(position);
            default -> null;
        };
        if (enemy == null)
            throw new IllegalArgumentException(
                    "Character \'" + character + "\' does not correspond to any known enemy");
        return enemy;
    }
}
