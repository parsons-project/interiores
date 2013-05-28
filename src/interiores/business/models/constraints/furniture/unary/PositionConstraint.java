package interiores.business.models.constraints.furniture.unary;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.backtracking.area.Area;
import interiores.business.models.constraints.furniture.PreliminarTrimmer;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * This constraint forces the furniture to be positioned in an exact point.
 * Precisely, the top left corner will be placed in the given point.
 * @author alvaro
 */
public class PositionConstraint
   extends UnaryConstraint implements PreliminarTrimmer
{
    
    private Point position;
    
    public PositionConstraint()
    { }
    
    public PositionConstraint(int x, int y) {
        position = new Point(x, y);
    }
    
    /**
     * Creator of the constraint
     * @param point The point where the furniture must be placed 
     */

    @Override
    public void preliminarTrim(FurnitureVariable variable) {
        // Implementar la mateixa idea que en les de parets
        variable.eliminateExceptP(new Area(new Rectangle()));
    }
    
}
