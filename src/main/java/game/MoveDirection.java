package game;

public enum MoveDirection {
    FORWARD,
    RIGHT,
    BACKWARD,
    LEFT;

    public String toString() {
        return switch (this) {
            case FORWARD -> "^";
            case BACKWARD -> "V";
            case LEFT -> "<";
            case RIGHT -> ">";
        };
    }

    public MapDirection clockwise() {
        return MapDirection.values()[
                (this.ordinal() + 1) % 4
                ];
    }

    public MapDirection anticlockwise() {
        return MapDirection.values()[
                (this.ordinal() - 1 + 4) % 4
                ];
    }

    public Vector2d toUnitVector() {
        int x = 0, y = 0;
        switch (this) {
            case BACKWARD -> y = -1;
            case FORWARD -> y = 1;
            case LEFT -> x = -1;
            case RIGHT -> x = 1;
        }
        return new Vector2d(x, y);
    }

    public MoveDirection directionWithRotation(MapDirection direction) {
        return MoveDirection.values()[
                (this.ordinal() + direction.ordinal()) % 4
                ];
    }
}
