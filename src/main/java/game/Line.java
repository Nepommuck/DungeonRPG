package game;

public class Line {
    protected final Vector2d a;
    protected final Vector2d b;

    public Line(int x1, int y1, int x2, int y2) {
        this(
                new Vector2d(x1, y1),
                new Vector2d(x2, y2)
        );
    }
    public Line(Vector2d a, Vector2d b) {
        this.a = a;
        this.b = b;
    }

    public Line addVector(Vector2d vector) {
        return new Line(
                a.add(vector),
                b.add(vector)
        );
    }

    @Override
    public String toString() {
        return a.toString() + " -> " + b.toString();
    }
}
