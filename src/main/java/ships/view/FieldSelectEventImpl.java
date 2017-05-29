package ships.view;

import ships.view.FieldSelectEvent;

public class FieldSelectEventImpl implements FieldSelectEvent {

    private final int row;
    private final int col;
    private final int button;

    public static final int NOBUTTON = 0;
    public static final int BUTTON1 = 1;
    public static final int BUTTON2 = 2;
    public static final int BUTTON3 = 3;

    public FieldSelectEventImpl(int row, int col, int button) {
        this.row = row;
        this.col = col;
        this.button = button;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @return the button
     */
    public int getButton() {
        return button;
    }

}
