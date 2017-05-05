package ships.model;

/**
 * @author Mateusz Kozlowski
 */
class ShipImpl implements Ship
{
    public static enum Size {
        ONE, TWO, THREE, FOUR
    }

    private Size size;
    private FieldImpl position;

    public ShipImpl(Size shipSize, FieldImpl startPosition) {
        this.size = shipSize;
        this.position = startPosition;
    }
}
