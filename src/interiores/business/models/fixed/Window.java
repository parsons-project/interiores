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
    
    private static final int WIND_DEPTH = 5;
    
    public Window(Point position, int length, Dimension roomDimension) {
        super("window", "window", position, new Dimension(length, WIND_DEPTH), "white",
              "glass", new SpaceAround(0, 0, 0, 0));
        
        // A window must always be in a wall
        this.addUnaryConstraint(new WallConstraint(roomDimension.width,
                                roomDimension.depth, Orientation.values()));
    }
    
}
