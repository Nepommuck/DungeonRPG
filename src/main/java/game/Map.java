package game;

import game.enemies.Enemy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Map {
    public final int width, height;
    private final ArrayList<ArrayList<MapElement>> mapArray;
    private Vector2d playerPosition;
    private final HashMap<Vector2d, Enemy> enemiesHashMap = new HashMap<>();

    public Map(String mapSrc) throws IOException {
        mapArray = new ArrayList<>();
        Vector2d mapSize =  readMap(mapSrc);
        width = mapSize.x;
        height = mapSize.y;
        System.out.println(this);
    }

    // For development purposes
    private Map(ArrayList<ArrayList<MapElement>> mapArray) {
        this.mapArray = mapArray;
        height = mapArray.size();
        width = mapArray.get(0).size();
    }

    private Vector2d readMap(String mapSrc) throws IOException {
        final ArrayList<Enemy> enemies = new ArrayList<>();
        int width = -1;
        int height = 0;
        BufferedReader reader = new BufferedReader(new FileReader(mapSrc));

        String line = reader.readLine();
        while (line != null) {
            mapArray.add(new ArrayList<>());
            height += 1;

            int len = line.length();
            if (width < 0)
                width = len;
            if (width != len)
                throw new IllegalArgumentException("Map doesn't have equal number of columns");

            for (char c : line.toCharArray()) {
                MapElement element = MapElementParser.parse(c);
                Vector2d position = new Vector2d(mapArray.get(height-1).size(), height - 1);

                if (element == MapElement.PLAYER) {
                    if (playerPosition != null)
                        throw new IllegalArgumentException("There cannot be 2 or more players on the map");

                    playerPosition = position;
                    element = MapElement.EMPTY;
                }
                else if (element == MapElement.ENEMY) {
                    Enemy newEnemy = MapElementParser.createEnemy(c, position);
                    enemies.add(newEnemy);
                }
                mapArray.get(height-1).add(element);
            }

            line = reader.readLine();
        }
        if (playerPosition == null)
            throw new IllegalArgumentException("There needs to be a player on a map");

        for (Enemy enemy : enemies) {
            Vector2d enemyPosition = enemy.getPosition();
            for (int dy = -1; dy <= 1; dy ++)
                for (int dx = -1; dx <= 1; dx++)
                    placeElementAtPosition(MapElement.ENEMY, enemyPosition.add(new Vector2d(dx, dy)));
            enemiesHashMap.put(enemyPosition, enemy);
        }

        return new Vector2d(width, height);
    }

    public Vector2d getInitialPlayerPosition() {
        return playerPosition;
    }

    public Vector2d getMaxPosition() {
        return new Vector2d(width - 1, height - 1);
    }

    public MapElement getElementAtPosition(Vector2d position) {
        if (!new Vector2d(0, 0).precedes(position) || !getMaxPosition().follows(position))
            return MapElement.WALL;
        return mapArray.get(position.y).get(position.x);
    }
    private void placeElementAtPosition(MapElement element, Vector2d position) {
        if (getElementAtPosition(position) == MapElement.EMPTY)
            mapArray.get(position.y).set(position.x, element);
    }

    public Enemy getEnemyFromPosition(Vector2d position) {
        for (int dy = -1; dy <= 1; dy ++)
            for (int dx = -1; dx <= 1; dx++) {
                Vector2d currentPosition = position.add(new Vector2d(dx, dy));
                if (enemiesHashMap.containsKey(currentPosition))
                    return enemiesHashMap.get(currentPosition);
            }
        return null;
    }

    public void removeEnemy(Enemy enemy) {
        Vector2d position = enemy.getPosition();
        System.out.println(position);

        enemiesHashMap.remove(position);
        System.out.println(this);
        mapArray.get(position.y).set(position.x, MapElement.EMPTY);
        System.out.println(this);
    }

    public Map getPlayerView(Vector2d playerPosition, MapDirection direction) {
        ArrayList<ArrayList<MapElement>> result = new ArrayList<>();
        for (int a = 0; a < 4; a++) {
            result.add(new ArrayList<>());

            for (int b = -3; b <= 3; b++) {
                Vector2d position = playerPosition.add(switch (direction) {
                    case NORTH -> new Vector2d(b, a);
                    case SOUTH -> new Vector2d(-b, -a);
                    case WEST -> new Vector2d(-a, b);
                    case EAST -> new Vector2d(a, -b);
                });
                result.get(a).add(
                        getElementAtPosition(position));
            }
        }
//        return result;
        return new Map(result);
    }

    public ArrayList<Wall> getWallList() {
        return getWallList(2, 0);
    }
    public ArrayList<Wall> getWallList(int dx, int dy) {
        ArrayList<Wall> result = new ArrayList<>();

        // Vertical walls
        for (int x = 0; x < width-1; x++) {
            int beginning = 0;
            while (beginning < height) {
                MapElement el1 = getElementAtPosition(new Vector2d(x, beginning));
                MapElement el2 = getElementAtPosition(new Vector2d(x + 1, beginning));

                int len = 0;
                MapElement lastEl1 = el1;
                MapElement lastEl2 = el2;

                while (beginning+len < height && exactlyOneWall(lastEl1, lastEl2) &&
                        ((el1 == MapElement.WALL && lastEl1 == el1) || (el2 == MapElement.WALL && lastEl2 == el2))) {
                    len++;
                    lastEl1 = getElementAtPosition(new Vector2d(x, beginning+len));
                    lastEl2 = getElementAtPosition(new Vector2d(x + 1, beginning+len));
                }
                if (len >= 1)
                    result.add(new Wall(
                            new Vector2d(x - dx, beginning - dy),
                            new Vector2d(x - dx, beginning+len - dy)
                    ));
                beginning += Math.max(1, len);
            }
        }

        // Horizontal walls
        for (int y = 0; y < height-1; y++) {
            int beginning = 0;
            while (beginning < width) {
                MapElement el1 = getElementAtPosition(new Vector2d(beginning, y));
                MapElement el2 = getElementAtPosition(new Vector2d(beginning, y+1));

                int len = 0;
                MapElement lastEl1 = el1;
                MapElement lastEl2 = el2;

                while (beginning+len < width && exactlyOneWall(lastEl1, lastEl2) &&
                        ((el1 == MapElement.WALL && lastEl1 == el1) || (el2 == MapElement.WALL && lastEl2 == el2))) {
                    len++;
                    lastEl1 = getElementAtPosition(new Vector2d(beginning+len, y));
                    lastEl2 = getElementAtPosition(new Vector2d(beginning+len, y+1));
                }
                if (len >= 1)
                    result.add(new Wall(
                            // Czemu -1, +1? Nie wiem, choć się domyślam
                            new Vector2d(beginning - dx-1, y - dy+1),
                            new Vector2d(beginning+len - dx-1, y - dy+1)
                    ));
                beginning += Math.max(1, len);
            }
        }
        return result;
    }
    private boolean exactlyOneWall(MapElement element1, MapElement element2) {
        return ((element1 == MapElement.WALL && element2 != MapElement.WALL) ||
                (element1 != MapElement.WALL && element2 == MapElement.WALL));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Map: \n\\x");
        for (int i = 0; i < width; i++)
            result.append((i < 10) ? " " : "").append(i);
        result.append("\ny+");
        result.append("-".repeat(2 * width));
        result.append('\n');

        for(int y = height-1; y >= 0; y--) {
            result.append(y).append("| ");

            for (MapElement el : mapArray.get(y))
                result.append(el.toString()).append(' ');
            result.append('\n');
//            y--;
        }
        return result.toString();
    }
}
