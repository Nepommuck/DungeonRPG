package game;

import game.enemies.Enemy;

public class GameEngine {
    private final App application;
    private final Map map;
    private final Player player;
    private boolean fighting = false;

    public GameEngine(App application, Map map) {
        this.application = application;
        this.map = map;
        this.player = new Player(map.getInitialPlayerPosition(), MapDirection.NORTH);

        updateWindow();
    }

    public void movePlayer(MoveDirection direction) {
        if (!fighting) {
            Vector2d goalPosition = player.getPosition().add(direction.
                    directionWithRotation(player.getDirection()).toUnitVector());
            if (map.getElementAtPosition(goalPosition) == MapElement.EMPTY) {
                application.onPlayerWalk();
                player.move(direction);
                updateWindow();
            } else {
                application.onPlayerWallHit();
            }
        }
    }
    public void rotatePlayer(boolean right) {
        if (!fighting) {
            if (right)
                player.rotateClockwise();
            else
                player.rotateAnticlockwise();
            updateWindow();
        }
    }

    private void updateWindow() {
        Enemy enemy = map.getEnemyFromPosition(player.getPosition());
        application.updateGameWindow(
                map.getPlayerView(player.getPosition(), player.getDirection()).getWallList(),
                player.getPlayerWeapon(),
                enemy
        );
        if (enemy != null) {
            fighting = true;
        }
    }
}
