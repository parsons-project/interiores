package interiores.business.models.fixed;

import interiores.business.models.Orientation;
import interiores.business.models.SpaceAround;
import interiores.business.models.WantedFixed;
import interiores.business.models.constraints.unary.WallConstraint;
import interiores.utils.Dimension;
import java.awt.Point;

/**
 * This class represents a door that the user wants to put in the room
 * @author alvaro
 */
public class Door extends WantedFixed {
    
    // All the doors will have the same depth so
    // only the width it's needed to create it
    private static final int DOOR_DEPTH = 5;
    
    public Door(Point position, int length, Dimension roomDimension) {
        super("door", "door", position, new Dimension(length, DOOR_DEPTH), "brown",
              "wood", new SpaceAround(length, 0, 0, 0));
        
        // A door must always be in a wall
        this.addUnaryConstraint(new WallConstraint(roomDimension.width,
                                roomDimension.depth, Orientation.values()));
    }
    
}
