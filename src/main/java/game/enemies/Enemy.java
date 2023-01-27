package game.enemies;

import game.Vector2d;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

abstract public class Enemy {
    private final Vector2d position;
    private int healthPoints;
    private final int damage;
    private final String imageName;

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
    public int getHealthPoints() {
        return healthPoints;
    }
    public int getDamage() {
        return damage;
    }
    public String getDeathSoundFileName() {
        return "enemy-death";
    }
    abstract public String toString();

}
