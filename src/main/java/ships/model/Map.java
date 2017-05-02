package ships.model;

public class Map {

    private static final int mapSize = 10;
    public static final int EMPTY = 0;
    public static final int SHIP = 1;
    public static final int FORBIDDEN = 2;
    public static final int CHECKED = 4;
    private Field[][] map;

    public Map() {
        map = new Field[mapSize][mapSize];
    }

    public Field[][] getPlayersMap() {
        return null;
    }

    public Field[][] getOpponentsMap() {
        return null;
    }

}
