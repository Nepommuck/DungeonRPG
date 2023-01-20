package game;

public class Wall {
    public final Vector2d a;
    public final Vector2d b;

    public Wall(int x1, int y1, int x2, int y2) {
        this(
                new Vector2d(x1, y1),
                new Vector2d(x2, y2)
        );
    }
    public Wall(Vector2d a, Vector2d b) {
        this.a = a;
        this.b = b;
    }

    public double edgeDistanceTo(Vector2d point) {
        return Math.min(
                this.a.distanceTo(point),
                this.b.distanceTo(point)
        );
    }
}
