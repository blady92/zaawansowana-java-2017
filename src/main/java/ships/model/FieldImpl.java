package ships.model;

/**
 * @author Mateusz Kozlowski
 */
public class FieldImpl implements Field
{
    public static enum State {
        Empty, Ship, Forbidden
    }

    private Boolean attacked;
    private State state;
    private int row,col;

    public FieldImpl(State state, int row, int col) {
        this.state = state;
    }

    public Boolean isShipHere() {
        return state == State.Ship;
    }

    public Boolean isAttacked() {
        return attacked;
    }
}
