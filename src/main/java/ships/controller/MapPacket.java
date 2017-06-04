/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import java.util.List;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import ships.model.Ship;

/**
 *
 * @author r4pt0r
 */
public class MapPacket extends CommunicationPacket {

    @JsonProperty("ships")
    protected List<Ship> ships;

    @JsonCreator
    public MapPacket(@JsonProperty("ships") List<Ship> ships) {
        this.ships = ships;
    }

    /**
     * @return the ships
     */
    public List<Ship> getShips() {
        return ships;
    }

}
