package interiores.business.models.constraints.furniture.unary;

import interiores.business.models.backtracking.Area.Area;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.InexhaustiveTrimmer;
import interiores.business.models.constraints.furniture.UnaryConstraint;
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
    extends UnaryConstraint implements InexhaustiveTrimmer {
    
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
     * Modifies the orientations defined for the constraint.
     * @param newOrientations The orientations that will override the previous ones
     */
    public void changePositions(Area newPositions) {
        validPositions = newPositions;
    }
    
    
    /**
     * Eliminates all values that do not fulfil the constraint.
     * @param variable The variable to be checked.
     */
    @Override
    public void preliminarTrim(FurnitureVariable variable) {
        variable.eliminateExceptP(validPositions);
    }
 

    @Override
    public boolean isSatisfied(FurnitureVariable variable) {
        return validPositions.contains(
                new Area(variable.getAssignedValue().getArea().getRectangle()));
    }
    
    @Override
    public String toString() {
        // @TODO Implement toString method in AreaConstraint
        return super.toString();
    }
}
