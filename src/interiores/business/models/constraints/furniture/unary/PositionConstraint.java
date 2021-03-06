package interiores.business.models.constraints.furniture.unary;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.backtracking.area.Area;
import interiores.business.models.constraints.furniture.InexhaustiveTrimmer;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import interiores.data.adapters.PointAdapter;
import java.awt.Point;
import java.awt.Rectangle;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * This constraint forces the furniture to be positioned in an exact point.
 * Precisely, the top left corner will be placed in the given point.
 * @author alvaro
 */
@XmlRootElement
public class PositionConstraint
   extends UnaryConstraint implements InexhaustiveTrimmer
{
    @XmlElement
    @XmlJavaTypeAdapter(PointAdapter.class)
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
        int maxSize = variable.getMaxSize();
        variable.eliminateExceptP(new Area(new Rectangle(position.x, position.y, maxSize, maxSize)));
    }

    @Override
    public boolean isSatisfied(FurnitureVariable variable) {
        return variable.assignedValue.getPosition().equals(position);
    }
    
    @Override
    public String toString() {
        return "Position (upper left corner): (" + position.x + ", " + position.y + ")"; 
    }
    
}
