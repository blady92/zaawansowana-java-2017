package ships.model;

import java.util.Objects;

/**
 * @author Mateusz Kozlowski
 */
public class FieldImpl implements Field {

    private Boolean attacked;
    private State state;
    private int row, col;

    public FieldImpl(State state, int row, int col) {
        this.state = state;
        this.row = row;
        this.col = col;
        this.attacked = Boolean.FALSE;
    }

    public FieldImpl(int row, int col) {
        this.state = State.EMPTY;
        this.row = row;
        this.col = col;
        this.attacked = Boolean.FALSE;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * Informs if field is occuped by a ship
     *
     * @return
     */
    public Boolean isShipHere() {
        return state == State.SHIP;
    }

    /**
     * Informs if field had been already shot by opponent
     *
     * @return <b>true</b> if field had been attacked
     */
    public Boolean isAttacked() {
        return attacked;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public Boolean attack() {
        if (state == State.SHIP) {
            this.attacked = true;
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Field) {
            Field obj = (Field) o;
            if (this.row == obj.getRow() && this.col == obj.getCol()) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return super.equals(o);
        }
    }

    public boolean same(Field f) {
        return this.getRow() == f.getRow()
                && this.getCol() == f.getCol()
                && this.state == f.getState()
                && Objects.equals(this.attacked, f.isAttacked());
    }
}
