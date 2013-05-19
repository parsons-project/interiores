/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.fixed;

import interiores.business.models.SpaceAround;
import interiores.business.models.WantedFixed;
import interiores.utils.Dimension;
import java.awt.Point;

/**
 * This class represents a solid block contained in the room.
 * @author alvaro
 */
public class Pillar
    extends WantedFixed
{
    public Pillar(Point position, Dimension dimension) {
        super("pillar", dimension, "gray", "brick", new SpaceAround(0, 0, 0, 0));
        
        setPosition(position);
    }
}
