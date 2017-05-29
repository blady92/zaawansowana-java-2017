package ships.view;

public interface FieldSelectEvent {

    /**
     * @return the row
     */
    int getRow();

    /**
     * @return the col
     */
    int getCol();

    /**
     * @return the button
     */
    int getButton();
}
