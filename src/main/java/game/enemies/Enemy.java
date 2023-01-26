package game.enemies;

import game.Vector2d;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Enemy {
    private final Vector2d position;
    public int healthPoints;
    public final int damage;
    public final String imageName;

    protected Enemy(int initalHP, int damage, String imageName, Vector2d position) {
        this.healthPoints = initalHP;
        this.damage = damage;
        this.imageName = imageName;
        this.position = position;
    }

    public void decreaseHealth(int hitValue) {
        this.healthPoints -= hitValue;
    }
    public boolean isAlive() {
        return healthPoints > 0;
    }

    public Image getImage() throws FileNotFoundException {
        return new Image(new FileInputStream("src\\main\\resources\\images\\" + imageName + ".png"));
    }

    public Vector2d getPosition() {
        return position;
    }
}