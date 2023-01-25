package game.weapon;

import game.Line;
import game.Vector2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class Sword extends Weapon {
    // Blade sprite points
    Vector2d A = new Vector2d(50, 50);
    Vector2d B = A.add(new Vector2d(200, 200));
    Vector2d C = A.add(new Vector2d(10, 50));
    Vector2d D = C.add(new Vector2d(170, 170));
    Vector2d E = A.add(new Vector2d(50, 10));
    Vector2d F = E.add(new Vector2d(170, 170));
    // Grip sprite points
    Vector2d G = B.add(new Vector2d(-50, 50));
    Vector2d H = B.add(new Vector2d(50, -50));
    Vector2d I = G.add(new Vector2d(20, 20));
    Vector2d J = H.add(new Vector2d(20, 20));
    Vector2d K = I.add(new Vector2d(35, -35));
    Vector2d L = J.add(new Vector2d(-35, 35));
    Vector2d M = K.add(new Vector2d(50, 50));
    Vector2d N = L.add(new Vector2d(50, 50));
    public Sword() {
        super(5);
    }

    @Override
    public ArrayList<Line> getSpriteLines(Vector2d offset) {
        ArrayList<Line> lines = new ArrayList<>();

        // Blade part
        lines.add(new Line(A, B));
        lines.add(new Line(A, C));
        lines.add(new Line(C, D));
        lines.add(new Line(A, E));
        lines.add(new Line(E, F));

        // Grip
        lines.add(new Line(G, H));
        lines.add(new Line(G, I));
        lines.add(new Line(H, J));
        lines.add(new Line(I, J));
        lines.add(new Line(K, M));
        lines.add(new Line(L, N));

        ArrayList<Line> results = new ArrayList<>();
        for (Line line : lines)
            results.add(line.addVector(offset));
        return results;
    }

    @Override
    public ArrayList<Vector2d> getSpritePoints(Vector2d offset) {
        ArrayList<Vector2d> points = new ArrayList<>(
                Arrays.asList(A, C, D, G, I, K, M, N, L, J, H, F, E)
        );
        ArrayList<Vector2d> results = new ArrayList<>();
        for (Vector2d point : points)
            results.add(point.add(offset));
        return results;
    }
}
