
package interiores.business.models.constraints;

import interiores.business.models.backtracking.FurnitureVariable;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * AreaConstraint represents a constraint imposed over the position of
 * a piece of furniture.
 */
public class AreaConstraint
    extends UnaryConstraint {
    
    /**
     * validPositions contains the positions a piece of furniture can have
     * in order to satisfy the constraint.
     * More precisely, by position of a furniture piece, we are talking about
     * his top left coordinate.
     */
    private List<Point> validPositions;
    
    /**
     * Creates a position constraint such that only those pieces of
     * furniture placed in one of the given positions will satisfy it
     * @param validPositions The positions that define the constraint
     */
    public AreaConstraint(List<Point> validPositions) {
        this.validPositions = validPositions;
    }
    
    /**
     * Eliminates all values that do not fulfil the constraint.
     * @param variable The variable to be checked.
     */
    @Override
    public void eliminateInvalidValues(FurnitureVariable variable) {        
        Iterator i = validPositions.iterator();
        while (i.hasNext())
            variable.domainPositions[0].remove( (Point) i.next());
    }
    
    /**
     * Modifies the orientations defined for the constraint.
     * @param newOrientations The orientations that will override the previous ones
     */
    public void changePositions(List<Point> newPositions) {
        validPositions = newPositions;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        
        result.append(this.getClass().getName() + NEW_LINE);
        
        result.append("Valid positions: ");
        for (Point point : validPositions) {
            result.append("(" + point.x + "," + point.y + ") ");
        }
        result.append(NEW_LINE);
        return result.toString();
    }
}
