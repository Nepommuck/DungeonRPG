package game;

import game.enemies.Enemy;

public class GameEngine {
    private final App application;
    private final Map map;
    private final Player player;
    private boolean fighting = false;
    private Enemy currentlyEngagedEnemy = null;

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

    public void attackEnemy() {
        currentlyEngagedEnemy.decreaseHealth(player.getPlayerWeapon().damage);
        application.onEnemyDamaged(currentlyEngagedEnemy, player.getPlayerWeapon());

        if (!currentlyEngagedEnemy.isAlive()) {
            map.removeEnemy(currentlyEngagedEnemy);
            currentlyEngagedEnemy = null;
            updateWindow();
        }
    }

    private void updateWindow() {
        currentlyEngagedEnemy = map.getEnemyFromPosition(player.getPosition());
        application.updateGameWindow(
                map.getPlayerView(player.getPosition(), player.getDirection()).getWallList(),
                player.getPlayerWeapon(),
                currentlyEngagedEnemy
        );
        if (currentlyEngagedEnemy != null) {
            fighting = true;
            application.setPanelToFightingMode(currentlyEngagedEnemy);
        }
        else {
            fighting = false;
            application.setPanelToWalkingMode();
        }
    }
}
