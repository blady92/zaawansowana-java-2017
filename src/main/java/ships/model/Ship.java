package ships.model;

/**
 * @author Mateusz Kozlowski
 */
class Ship {

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
    public Boolean getDirection() {
        return direction;
    }

    public enum Size {
        ONE, TWO, THREE, FOUR
    }

    private Size size;
    private Field position;
    private Boolean direction;

    /**
     * @param shipSize
     * @param startPosition
     * @param direction     <b>true</b> if vertical, <b>false</b> otherwise
     */
    public Ship(Size shipSize, Field startPosition, Boolean direction) {
        this.size = shipSize;
        this.position = startPosition;
        this.direction = direction;
    }

    public Integer getSizeAsInteger() {
        switch (size) {
            case ONE:
                return 1;
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            default:
                throw new IllegalStateException(
                        "There is no other size, it should have never happened"
                );
        }
    }
}
