package game;


public enum MapElement {
    EMPTY,
    WALL,
    PLAYER,
    ENEMY;

    @Override
    public String toString() {
        return switch (this) {
            case EMPTY -> " ";
            case WALL -> "#";
            case PLAYER -> "P";
            case ENEMY -> "E";
        };
    }
}
