package ships.model;

/**
 * @author Mateusz Kozlowski
 */
public class FieldImpl implements Field {

    private Boolean attacked;
    private State state;
    private int row,col;

    public FieldImpl(State state, int row, int col) {
        this.state = state;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Boolean isShipHere() {
        return state == State.SHIP;
    }

    public Boolean isAttacked() {
        return attacked;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void attack() {
        this.attacked = true;
    }
}
