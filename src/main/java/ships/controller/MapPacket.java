/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import java.util.List;
import ships.model.Ship;

/**
 *
 * @author r4pt0r
 */
public class MapPacket extends CommunicationPacket {

    protected List<Ship> ships;

    public MapPacket(List<Ship> ships) {
        this.ships = ships;
    }

    /**
     * @return the ships
     */
    public List<Ship> getShips() {
        return ships;
    }

}
