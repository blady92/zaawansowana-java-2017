package ships.view;

public interface BattleshipMapClickObserver {

    /**
     * Method called when BattleshipMap clicked
     * @param fce
     */
    public void fieldClickedEvent(FieldSelectEvent fce, BattleshipsMap bm);

}
