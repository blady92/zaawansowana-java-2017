package ships.model;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Mateusz Kozlowski
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonProperty("shipSize")
    private Size size;

    @JsonProperty("startPos")
    private Field position;

    @JsonProperty("direction")
    private Direction direction;

    private Integer lives;

    /**
     * @param shipSize
     * @param startPosition
     * @param direction     <b>true</b> if vertical, <b>false</b> otherwise
     */
    @JsonCreator
    public Ship(
            @JsonProperty("shipSize") Size shipSize,
            @JsonProperty("startPos") Field startPosition,
            @JsonProperty("direction") Direction direction
    ) {
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

    public List<Field> getFieldList() {
        List<Field> result = new ArrayList<>();
        for (int i = 0; i < this.getSize().getSize(); i++) {
            if (this.direction == Direction.HORIZONTAL) {
                result.add(new FieldImpl(
                        position.getRow(), position.getCol() + i));
            }
            else {
                result.add(new FieldImpl(
                        position.getRow() + i, position.getCol()));
            }
        }
        return result;
    }

}
