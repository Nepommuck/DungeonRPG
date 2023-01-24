package game;

public class GameEngine {
    private App application;
    private Map map;
    private Player player;

    public GameEngine(App application, Map map) {
        this.application = application;
        this.map = map;
        this.player = new Player(map.getInitialPlayerPosition(), MapDirection.NORTH);

        updateWindow();
    }

    public void movePlayer(MoveDirection direction) {
        player.move(direction);
        updateWindow();
    }
    public void rotatePlayer(boolean right) {
        if (right)
            player.rotateClockwise();
        else
            player.rotateAnticlockwise();
        updateWindow();
    }

    private void updateWindow() {
        application.updateGameWindow(map.getPlayerView(
                player.getPosition(), player.getDirection()).getWallList()
        );
    }
}
