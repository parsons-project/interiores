package interiores.business.models.fixed;

import interiores.business.models.Orientation;
import interiores.business.models.SpaceAround;
import interiores.business.models.WantedFixed;
import interiores.business.models.constraints.unary.WallConstraint;
import interiores.utils.Dimension;
import java.awt.Point;

/**
 *
 * @author alvaro
 */
public class Window extends WantedFixed {
    
    public Window(Point position, Dimension dimension, Dimension roomDimension) {
        super("window", "window", position, dimension, "white",
              "glass", new SpaceAround(0, 0, 0, 0));
        
        // A window must always be in a wall
        this.addUnaryConstraint(new WallConstraint(roomDimension.width,
                                roomDimension.depth, Orientation.values()));
    }
    
}
