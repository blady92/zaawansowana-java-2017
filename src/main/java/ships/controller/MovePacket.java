/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import ships.model.Field;

/**
 *
 * @author r4pt0r
 */
public class MovePacket extends CommunicationPacket {

    @JsonProperty("field") protected Field field;

    @JsonCreator
    public MovePacket(@JsonProperty("field") Field f) {
        this.field = f;
    }

    /**
     * @return the field
     */
    public Field getField() {
        return field;
    }

}
