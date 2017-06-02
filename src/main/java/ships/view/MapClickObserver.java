package ships.view;

public interface MapClickObserver {

    /**
     * Method called when BattleshipMap clicked
     * @param fce
     */
    void fieldClickedEvent(FieldSelectEvent fce, MapView bm);

}
