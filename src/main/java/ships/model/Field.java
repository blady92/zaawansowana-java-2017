package ships.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * @author Mateusz Kozlowski
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = FieldImpl.class, name = "Field")
})
public interface Field {

    enum State {
        EMPTY, SHIP, FORBIDDEN
    }

    /**
     * Informs if field is occuped by a ship
     *
     * @return
     */
    Boolean isShipHere();

    /**
     * Informs if field had been already shot by opponent
     *
     * @return <b>true</b> if field had been attacked
     */
    Boolean isAttacked();

    int getRow();

    int getCol();

    void setState(State state);

    State getState();

    Boolean attack();

    boolean same(Field f);
}
