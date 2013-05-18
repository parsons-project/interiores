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
 *
 * @author alvaro
 */
public class Pillar extends WantedFixed {
    public Pillar(Point position, Dimension dimension) {
        super("pillar", "pillar", position, dimension, "gray",
              "brick", new SpaceAround(0, 0, 0, 0));
    }
}
