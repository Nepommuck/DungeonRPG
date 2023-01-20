package game;

public enum DrawDirection {
    UP,
    RIGHT,
    BOTTOM,
    LEFT;

    public DrawDirection clockwise() {
        return DrawDirection.values()[
                (this.ordinal() + 1) % 4
            ];
    }

    public DrawDirection antiClockwise() {
        return DrawDirection.values()[
                (this.ordinal() - 1 + 4) % 4
            ];
    }
}
