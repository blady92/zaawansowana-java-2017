package ships.view;

public class FieldClickEventImpl implements FieldClickEvent {

    public static final int NOBUTTON = 0;
    public static final int BUTTON1 = 1;
    public static final int BUTTON2 = 2;
    public static final int BUTTON3 = 3;

    private final int row;
    private final int col;
    private final int button;

    public FieldClickEventImpl(int row, int col, int button) {
        this.row = row;
        this.col = col;
        this.button = button;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public int getButton() {
        return button;
    }
}
