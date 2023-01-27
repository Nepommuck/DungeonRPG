package game;

import game.weapons.Sword;
import game.weapons.Weapon;

public class Player {
    private Vector2d position;
    private MapDirection direction;
    private int healthPoints;
    private final int maxHealthPoints;

    private final Weapon weapon = new Sword();

    public Player(Vector2d initialPosition, MapDirection initialDirection) {
        position = initialPosition;
        direction = initialDirection;
        maxHealthPoints = 20;
        healthPoints = maxHealthPoints;
    }

    public void rotateClockwise() {
        direction = direction.clockwise();
    }
    public void rotateAnticlockwise() {
        direction = direction.anticlockwise();
    }

    public void move() {
        move(MoveDirection.FORWARD);
    }
    public void move(MoveDirection moveDirection) {
        position = position.add(moveDirection.
                directionWithRotation(direction).toUnitVector());
    }

    public void damage(int damage) {
        this.healthPoints -= damage;
    }

    public MapDirection getDirection() {
        return direction;
    }
    public Vector2d getPosition() {
        return position;
    }
    public Weapon getPlayerWeapon() {
        return weapon;
    }
    public int getHealthPoints() {
        return healthPoints;
    }
    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }
}
