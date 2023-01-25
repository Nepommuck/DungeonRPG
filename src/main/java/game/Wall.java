package game;

public class Wall extends Line {

    public Wall(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }
    public Wall(Vector2d a, Vector2d b) {
        super(a, b);
    }

    public double edgeDistanceTo(Vector2d point) {
        return Math.min(
                this.a.distanceTo(point),
                this.b.distanceTo(point)
        );
    }
    public double smallestDistanceTo(Vector2d point) {
        return getClosestPoint(point).distanceTo(point);
    }
    public int getXdistanceTo(Vector2d point) {
        return Math.abs(point.x - getClosestPoint(point).x);
    }
    private Vector2d getClosestPoint(Vector2d point) {
        Vector2d closestPoint;

        if (this.a.y == this.b.y)
            closestPoint = new Vector2d(point.x, this.a.y);
        else
            closestPoint = new Vector2d(this.a.x, point.y);
        int minX = Math.min(this.a.x, this.b.x);
        int maxX = Math.max(this.a.x, this.b.x);
        int minY = Math.min(this.a.y, this.b.y);
        int maxY = Math.max(this.a.y, this.b.y);
        // This point is indeed part of a wall
        if (minX <= closestPoint.x && closestPoint.x <= maxX && minY <= closestPoint.y && closestPoint.y <= maxY)
            return closestPoint;

        if (this.a.distanceTo(point) < this.b.distanceTo(point))
            return this.a;
        return this.b;
    }
}
