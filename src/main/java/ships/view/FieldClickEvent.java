package ships.view;

public interface FieldClickEvent {

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
