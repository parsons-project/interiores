package interiores.business.models.constraints.furniture.unary;

import interiores.business.models.backtracking.Area.Area;
import interiores.business.models.backtracking.Domain;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.UnaryConstraint;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * AreaConstraint represents a constraint imposed over the position of
 * a piece of furniture.
 */
@XmlRootElement
public class AreaConstraint
    extends UnaryConstraint {
    
    /**
     * validPositions contains the positions a piece of furniture can have
     * in order to satisfy the constraint.
     * More precisely, by position of a furniture piece, we are talking about
     * his top left coordinate.
     */
    @XmlElementWrapper
    private Area validPositions;
    
    public AreaConstraint() {
        
    }
    
    /**
     * Creates a position constraint such that only those pieces of
     * furniture placed in one of the given positions will satisfy it
     * @param validPositions The positions that define the constraint
     */
    public AreaConstraint(Area validPositions) {
        this.validPositions = validPositions;
    }
    
    /**
     * Eliminates all values that do not fulfil the constraint.
     * @param variable The variable to be checked.
     */
    @Override
    public void eliminateInvalidValues(Domain domain) {
        domain.getPositions(0).intersection(validPositions);
    }
    
    /**
     * Modifies the orientations defined for the constraint.
     * @param newOrientations The orientations that will override the previous ones
     */
    public void changePositions(Area newPositions) {
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
