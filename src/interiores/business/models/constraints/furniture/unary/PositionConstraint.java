package interiores.business.models.constraints.furniture.unary;

import interiores.business.models.backtracking.area.Area;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * This constraint forces the furniture to be positioned in an exact point.
 * Precisely, the top left corner will be placed in the given point.
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
        super(new Area(new Rectangle(point.x, point.y, 5, 5)));
    }
    
}
