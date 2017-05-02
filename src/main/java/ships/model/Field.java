package ships.model;

public class Field {

    private Boolean business, attacked;

    public Field(Boolean busy) {
        this.business = busy;
    }

    public Boolean isShipHere() {
        return business;
    }

    public Boolean isAttacked() {
        return attacked;
    }

}
