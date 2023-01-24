package game;

import static game.MapElement.EMPTY;
import static game.MapElement.WALL;
import static game.MapElement.PLAYER;

public class MapElementParser {
    public static MapElement parse(char character) {
        MapElement result = switch (character) {
            case '.' -> EMPTY;
            case '#' -> WALL;
            case 'P' -> PLAYER;
            default -> null;
        };
        if (result == null)
            throw new IllegalArgumentException("Illegal character \'" + character + "\' in map file");
        return result;
    }
}
