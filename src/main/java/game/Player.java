package game;

import game.weapon.Sword;
import game.weapon.Weapon;

public class Player {
    private Vector2d position;
    private MapDirection direction;

    private final Weapon weapon = new Sword();

    public Player(Vector2d initialPosition, MapDirection initialDirection) {
        position = initialPosition;
        direction = initialDirection;
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

    public MapDirection getDirection() {
        return direction;
    }

    public Vector2d getPosition() {
        return position;
    }

    public Weapon getPlayerWeapon() {
        return weapon;
    }
}
