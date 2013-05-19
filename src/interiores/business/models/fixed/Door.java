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
public class Door extends WantedFixed {
    
    private static final int DOOR_DEPTH = 5;
    
    public Door(Point position, int length, Dimension roomDimension) {
        super("door", "door", position, new Dimension(length, DOOR_DEPTH), "brown",
              "wood", new SpaceAround(length, 0, 0, 0));
        
        // A door must always be in a wall
        this.addUnaryConstraint(new WallConstraint(roomDimension.width,
                                roomDimension.depth, Orientation.values()));
    }
    
}
