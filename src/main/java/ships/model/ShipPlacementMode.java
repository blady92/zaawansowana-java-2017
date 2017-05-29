package ships.model;

/**
 *
 * @author r4pt0r
 */
public class ShipPlacementMode {

    private Ship.Size size = Ship.Size.ONE;
    private Ship.Direction dir = Ship.Direction.HORIZONTAL;
    private Boolean active = Boolean.FALSE;

    /**
     * @return the size
     */
    public Ship.Size getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Ship.Size size) {
        this.size = size;
    }

    /**
     * @return the dir
     */
    public Ship.Direction getDir() {
        return dir;
    }

    /**
     * @return the active
     */
    public Boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = Boolean.TRUE;
    }

    public void deactivate() {
        this.active = Boolean.FALSE;
    }

    public void switchDirection() {
        if (dir == Ship.Direction.HORIZONTAL) {
            dir = Ship.Direction.VERTICAL;
        } else {
            dir = Ship.Direction.HORIZONTAL;
        }
    }

}
