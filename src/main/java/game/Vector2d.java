package game;

import java.util.Objects;


public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "("+this.x+", "+this.y+")";
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(
                this.x + other.x,
                this.y + other.y
        );
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(
                this.x - other.x,
                this.y - other.y
        );
    }
    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(
                Math.max(this.x, other.x),
                Math.max(this.y, other.y)
        );
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(
                Math.min(this.x, other.x),
                Math.min(this.y, other.y)
        );
    }

    public Vector2d opposite() {
        return new Vector2d( -this.x, -this.y );
    }

    public double distanceTo(Vector2d other) {
        return Math.sqrt(
                Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2)
        );
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass())
            return false;
        Vector2d oth = (Vector2d) other;
        return this.x == oth.x && this.y == oth.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

}
