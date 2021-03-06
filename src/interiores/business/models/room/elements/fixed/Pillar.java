/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.room.elements.fixed;

import interiores.business.models.Orientation;
import interiores.business.models.SpaceAround;
import interiores.business.models.room.FurnitureModel;
import interiores.business.models.room.elements.WantedFixed;
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
        super("pillar", position, new FurnitureModel("pillar", dimension, 0.0f, "gray", "brick",
                new SpaceAround(0, 0, 0, 0)), Orientation.S);
    }
}
