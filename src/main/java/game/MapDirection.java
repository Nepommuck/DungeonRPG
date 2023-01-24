package game;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public String toString() {
        return switch (this) {
            case NORTH -> "^";
            case SOUTH -> "V";
            case WEST -> "<";
            case EAST -> ">";
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
            case SOUTH -> y = -1;
            case NORTH -> y = 1;
            case WEST -> x = -1;
            case EAST -> x = 1;
        }
        return new Vector2d(x, y);
    }
}
