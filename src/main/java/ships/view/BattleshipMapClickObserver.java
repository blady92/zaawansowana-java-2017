package ships.view;

public interface BattleshipMapClickObserver {
    /**
     * Method called when BattleshipMap clicked
     * @param fce
     */
    public void fieldClickedEvent(FieldClickEvent fce, BattleshipsMap bm);
}
