package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;


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


    public void drawWalls(ArrayList<Wall> walls) {
        walls.sort((w1, w2) -> {
            double maxDepth1 = Math.max(calcDepth(w1.a), calcDepth(w1.b));
            double maxDepth2 = Math.max(calcDepth(w2.a), calcDepth(w2.b));
            if (maxDepth1 != maxDepth2)
                return (int) -Math.signum(maxDepth1 - maxDepth2);
            double minDepth1 = Math.min(calcDepth(w1.a), calcDepth(w1.b));
            double minDepth2 = Math.min(calcDepth(w2.a), calcDepth(w2.b));
            if (minDepth1 != minDepth2)
                return (int) -Math.signum(minDepth1 - minDepth2);
            return -(Math.abs(w1.a.x) - Math.abs(w2.a.x));
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
    private double calcDepth(Vector2d mapPosition) {
        int sharesSum = 18;
        int diagonalShare = switch (mapPosition.y) {
            case 1 -> 6;
            case 2 -> 11;
            case 3 -> 14;
            case 4 -> 16;
            default -> sharesSum;
        };
        if (mapPosition.y <= 0)
            diagonalShare = 0;
        return (double) diagonalShare / sharesSum;
    }
    private Vector2d mapPositionToScreenPosition(Vector2d mapPosition) {
        double diagPerc = calcDepth(mapPosition);
        double length = this.windowSize - diagPerc * windowSize;

        return new Vector2d(
            (int) (diagPerc * windowSize/2 + (mapPosition.x * length)),
            (int) (diagPerc * windowSize/2 - this.marginTop)
        );
    }
}
