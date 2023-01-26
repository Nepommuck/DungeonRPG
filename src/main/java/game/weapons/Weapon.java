package game.weapons;

import game.Line;
import game.Vector2d;

import java.util.ArrayList;

public abstract class Weapon {
    public final int damage;

    Weapon(int damage) {
        this.damage = damage;
    }

    public ArrayList<Line> getSpriteLines() {
        return getSpriteLines(new Vector2d(0, 0));
    }

    abstract public ArrayList<Line> getSpriteLines(Vector2d offset);

    abstract public ArrayList<Vector2d> getSpritePoints(Vector2d offset);

    abstract public String getHitSoundFileName();
}
