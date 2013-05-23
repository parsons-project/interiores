package interiores.business.models.constraints.binary;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.Room;
import interiores.business.models.backtracking.Area.Area;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.backtracking.InterioresVariable;
import interiores.business.models.constraints.BinaryConstraint;
import interiores.core.Debug;
import interiores.utils.Dimension;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Constraint representing a minimum separation between two variables
 * @author alvaro
 */
@XmlRootElement
public class MinDistanceConstraint 
                    extends BinaryConstraint {
    
    @XmlAttribute
    private int distance; // The minimum distance between the two variables
    
    public MinDistanceConstraint() {
        
    }
    
    public MinDistanceConstraint(int distance) {
        this.distance = distance;
    }
    
    /**
     * Checks whether two variables satisfy the constraint
     * @param fvariable1 The first variable
     * @param fvariable2 The second variable
     * @return 'true' if the two variables are separated by, at least, 'distance' cm. 'false' otherwise
     */
    @Override
    public boolean isSatisfied(InterioresVariable fvariable1, FurnitureVariable fvariable2) {
           
        OrientedRectangle rectangle1 = ((FurnitureValue) fvariable1.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) fvariable2.getAssignedValue()).getArea();
       
        return Area.distance(rectangle1, rectangle2) > distance;
    }
    
    @Override
    public String toString() {
        return "Minimum distance = " + distance + "cm constraint";
    }

    /**
     * trims the domain of toTrimVariable according to the value of
     * assignedVariable and the restriction.
     * So far, the trim is approximated. The round shape of a radius is
     * approximated by a straight(stair shaped) line.
     * @param assignedVariable the variable with an assigned value
     * @param toTrimVariable the variable whose domain has to be trimmed
     */
    @Override
    public void trim(InterioresVariable assignedVariable, FurnitureVariable toTrimVariable, OrientedRectangle roomArea) {
        Area modelArea = new Area(assignedVariable.assignedValue.getArea());
        Area invalidArea = modelArea.rectangleAround(distance);
        toTrimVariable.exclude(invalidArea);
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
    public void preliminarTrim(InterioresVariable variable1, InterioresVariable variable2, OrientedRectangle roomArea) {
        //Not implemented for now. This shouldn't have an important impact.
    }

    /**
     * Estimation of weight of this constraint.
     * @return 
     */
    @Override
    public int getWeight(OrientedRectangle roomArea) {
        if (distance > 500) return 150;
        if (distance > 300) return 90;
        if (distance > 200) return 50;
        if (distance > 100) return 20;
        if (distance > 50) return 8;
        return 5;
    }

    

}
