package ships.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mateusz Kozlowski
 */
public class Ship {

    /**
     * @return the size
     */
    public Size getSize() {
        return size;
    }

    /**
     * @return the position
     */
    public Field getPosition() {
        return position;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    public enum Size {
        ONE(1), TWO(2), THREE(3), FOUR(4);

        private final int size;

        Size(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    public enum Direction {
        HORIZONTAL, VERTICAL
    }

    private Size size;
    private Field position;
    private Direction direction;
    private Integer lives;

    /**
     * @param shipSize
     * @param startPosition
     * @param direction     <b>true</b> if vertical, <b>false</b> otherwise
     */
    public Ship(Size shipSize, Field startPosition, Direction direction) {
        this.size = shipSize;
        this.position = startPosition;
        this.direction = direction;
        this.lives = shipSize.getSize();
    }

    public void hitTheShip() {
        if (lives > 0) {
            lives--;
        }
    }

    public boolean isSunken() {
        return lives == 0;
    }
}
