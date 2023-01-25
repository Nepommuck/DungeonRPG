package game;

public class GameEngine {
    private final App application;
    private final Map map;
    private final Player player;

    public GameEngine(App application, Map map) {
        this.application = application;
        this.map = map;
        this.player = new Player(map.getInitialPlayerPosition(), MapDirection.NORTH);

        updateWindow();
    }

    public void movePlayer(MoveDirection direction) {
        Vector2d goalPosition = player.getPosition().add(direction.
                directionWithRotation(player.getDirection()).toUnitVector());
        if (map.getElementAtPosition(goalPosition) == MapElement.EMPTY) {
            player.move(direction);
            updateWindow();
        }
        else
            System.out.println("PUNCH");
    }
    public void rotatePlayer(boolean right) {
        if (right)
            player.rotateClockwise();
        else
            player.rotateAnticlockwise();
        updateWindow();
    }

    private void updateWindow() {
        application.updateGameWindow(
                map.getPlayerView(player.getPosition(), player.getDirection()).getWallList(),
                player.getPlayerWeapon()
        );
    }
}
