package interiores.business.models.constraints.unary;

import interiores.business.models.backtracking.Domain;
import interiores.business.models.constraints.UnaryConstraint;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * 
 * AreaConstraint represents a constraint imposed over the position of
 * a piece of furniture.
 */
@XmlRootElement
@XmlSeeAlso({PositionConstraint.class, WallConstraint.class})
public class AreaConstraint
    extends UnaryConstraint {
    
    /**
     * validPositions contains the positions a piece of furniture can have
     * in order to satisfy the constraint.
     * More precisely, by position of a furniture piece, we are talking about
     * his top left coordinate.
     */
    @XmlElementWrapper
    private List<Point> validPositions;
    
    public AreaConstraint() {
        
    }
    
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
    public void eliminateInvalidValues(Domain domain) {        
        Iterator it = domain.getPositions(0).iterator();
        
        List<Point> invalidPositions = new ArrayList();
        
        while(it.hasNext()) {
            Point p = (Point) it.next();
            if(! validPositions.contains(p))
                invalidPositions.add(p);
        }
        
        for(Point position : invalidPositions)
            domain.getPositions(0).remove(position);
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
        return "Position area: from " + validPositions.get(0) + " to " + validPositions.get(validPositions.size()-1);
    }
}
