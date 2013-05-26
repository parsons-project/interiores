package interiores.business.models.constraints.unary;

import java.awt.Point;
import java.util.Arrays;

/**
 * This constraint forces the furniture to be positioned in an exact point
 * @author alvaro
 */
public class PositionConstraint
    extends AreaConstraint
{
    
    private Point position;
    
    public PositionConstraint()
    { }
    
    /**
     * Creator of the constraint
     * @param point The point where the furniture must be placed 
     */
    public PositionConstraint(Point point) {
        super(Arrays.asList(point));
        position = point;
    }
    
    @Override
    public String toString() {
        return "Fixed to:  x = " + position.x + ", y = " + position.y;
    }
    
}
