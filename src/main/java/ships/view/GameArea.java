/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author r4pt0r
 */
public class GameArea extends Canvas {

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        grphcs.setColor(Color.MAGENTA);
        Rectangle bounds = this.getBounds();
        grphcs.fillRect(0, 0, bounds.width, bounds.height);
    }
    
}
