package ships.model;

import java.util.Objects;

/**
 * @author Mateusz Kozlowski
 */
public class FieldImpl implements Field {

    private Boolean attacked;
    private State state;
    private int row,col;

    public FieldImpl(State state, int row, int col) {
        this.state = state;
        this.row = row;
        this.col = col;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldImpl field = (FieldImpl) o;
        return row == field.row &&
                col == field.col &&
                Objects.equals(attacked, field.attacked) &&
                state == field.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attacked, state, row, col);
    }
}
