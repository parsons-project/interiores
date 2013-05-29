package interiores.business.models.constraints.furniture.binary;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.area.Area;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.BinaryConstraintEnd;
import interiores.business.models.constraints.furniture.PreliminarTrimmer;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Constraint representing a minimum separation between two variables
 * @author alvaro
 */
@XmlRootElement
public class MinDistanceConstraint 
    extends BinaryConstraintEnd implements PreliminarTrimmer {
    
    @XmlAttribute
    private int distance; // The minimum distance between the two variables
    
    
    public MinDistanceConstraint(int distance) {
        super();
        this.distance = distance;
    }
    
    @Override
    public String toString() {
        return "Minimum distance = " + distance + "cm constraint";
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::   

 
    /**
     * Estimation of weight of this constraint.
     * @return 
     */
    @Override
    public int getWeight() {
        if (distance > 500) return 150;
        if (distance > 300) return 90;
        if (distance > 200) return 50;
        if (distance > 100) return 20;
        if (distance > 50) return 8;
        return 5;
    }

    @Override
    public void trim2(FurnitureVariable variable) {
        Area invalidArea = new Area(otherVariable.assignedValue.getArea());
        invalidArea.expand(distance/2);
        variable.trimP(invalidArea);
    }

    
    /**
     * We can eliminate the positions such that if one of the variables is
     * there, the whole room is too close to this variable for the other
     * variable to be placed.
     * This is, intuitively, the center of the room, if the distance is large
     * enough.
     * The positions eliminated are those that fall in the intersection of 4
     * sheres of radius distance placed in the corners of the room.
     * @param variable1
     * @param variable2 
     */
    @Override
    public void preliminarTrim(FurnitureVariable variable) {
         //Not implemented for now. This shouldn't have an important impact.
    }

    /**
     * Checks whether the variable satisfies the constraint
     * @return 'true' if the two variables are separated by, at least,
     * 'distance' cm. 'false' otherwise
     */
    @Override
    public boolean isSatisfied2(FurnitureVariable variable) {
           
        OrientedRectangle rectangle1 =
                ((FurnitureValue) variable.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 =
                ((FurnitureValue) otherVariable.getAssignedValue()).getArea();
       
        return Area.distance(rectangle1, rectangle2) >= distance;
    }

}
