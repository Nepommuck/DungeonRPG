package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;


public class GameWindow extends Canvas {
    final int windowSize, marginTop, marginBottom;
    final int width, height;
    final GraphicsContext context;
    final Color backgroundColor = Color.BLACK;
    final Color drawingColor = Color.WHITESMOKE;

    public GameWindow(int windowSize, int marginTop, int marginBottom) {
        super(windowSize, windowSize - (marginTop + marginBottom));

        this.windowSize = windowSize;
        this.marginTop = marginTop;
        this.marginBottom = marginBottom;

        this.width = windowSize;
        this.height = windowSize - (marginTop + marginBottom);

        this.context = this.getGraphicsContext2D();
        this.context.setFill(this.backgroundColor);
        this.context.fillRect(0, 0, width, height);
    }

    public void clear() {
        this.context.setFill(this.backgroundColor);
        this.context.fillRect(0, 0, width, height);
    }

    public void drawTestLine() {
        this.context.setStroke(this.drawingColor);
        this.context.setLineWidth(2);
        this.context.strokeLine(40, 10, 10, 40);
    }

    public void drawBox(int depth) {
        drawBox(depth, 0);
    }
    public void drawBox(int depth, int dx) {
        this.context.setStroke(this.drawingColor);
        this.context.setLineWidth(2);

        int[] diagShares = {6, 11, 14, 16};
        int sharesSum = 18;

        double diagPerc = (double) diagShares[depth] / sharesSum;

        double length = this.windowSize - diagPerc * windowSize;

        double x = diagPerc * windowSize/2 + (dx * length);
        double y = diagPerc * windowSize/2 - this.marginTop;

        this.context.fillPolygon(
                new double[]{x, x, x + length, x + length},
                new double[]{y, y + length, y + length, y},
                4
        );

        // upper
        this.context.strokeLine(x, y, x + length, y);
        // lower
        this.context.strokeLine(x, y + length, x + length, y + length);
        // left
        this.context.strokeLine(x, y, x, y + length);
        // right
        this.context.strokeLine(x + length, y, x + length, y + length);
    }

    public void drawWalls(Wall[] walls) {
        Arrays.sort(walls, (w1, w2) -> {
            Vector2d observer = new Vector2d(0, -1);
            return (int) -Math.signum(w1.edgeDistanceTo(observer) - w2.edgeDistanceTo(observer));
        });

        for (Wall wall : walls) {
            drawWall(wall);
        }
    }

    public void drawWall(Wall wall) {
        Vector2d aTop = mapPositionToScreenPosition(wall.a);
        Vector2d aBottom = new Vector2d(aTop.x, this.height - aTop.y);

        Vector2d bTop = mapPositionToScreenPosition(wall.b);
        Vector2d bBottom = new Vector2d(bTop.x, this.height - bTop.y);

        // Clear area
        this.context.fillPolygon(
                new double[]{aTop.x, bTop.x, bBottom.x, aBottom.x},
                new double[]{aTop.y, bTop.y, bBottom.y, aBottom.y},
                4
        );

        // Upper line
        drawLine(aTop, bTop);
        // Bottom line
        drawLine(aBottom, bBottom);
        // Left line
        drawLine(aTop, aBottom);
        // Right lien
        drawLine(bTop, bBottom);
    }

    private void drawLine(Vector2d start, Vector2d end) {
        this.context.setStroke(this.drawingColor);
        this.context.setLineWidth(2);
        this.context.strokeLine(start.x, start.y, end.x, end.y);
    }
    private Vector2d mapPositionToScreenPosition(Vector2d mapPosition) {
        int sharesSum = 18;
        int diagonalShare = switch (mapPosition.y) {
            case 1 -> 6;
            case 2 -> 11;
            case 3 -> 14;
            case 4 -> 16;
            default -> sharesSum;
        };
        if (mapPosition.y <= 0)
            diagonalShare = -1;

        double diagPerc = (double) diagonalShare / sharesSum;
        double length = this.windowSize - diagPerc * windowSize;

        return new Vector2d(
            (int) (diagPerc * windowSize/2 + (mapPosition.x * length)),
            (int) (diagPerc * windowSize/2 - this.marginTop)
        );
    }
}
