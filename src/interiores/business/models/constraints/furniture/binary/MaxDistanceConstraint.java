package interiores.business.models.constraints.furniture.binary;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.Room;
import interiores.business.models.backtracking.Area.Area;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.backtracking.InterioresVariable;
import interiores.business.models.constraints.furniture.BinaryConstraint;
import interiores.utils.Dimension;
import java.awt.Rectangle;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Constraint representing a maximum separation between two variables
 * @author alvaro
 */
@XmlRootElement
public class MaxDistanceConstraint 
                extends BinaryConstraint {
    
    @XmlAttribute
    private int distance; // The maximum distance between the two variables
    
    public MaxDistanceConstraint() {
        
    }
    
    public MaxDistanceConstraint(int distance) {
        this.distance = distance;
    }
    
    
    @Override
    public String toString() {
        return "Maximum distance = " + distance + "cm constraint";
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::    
    
    /**
     * Trims the domain of variable according to the value of
     * the other variable and the restriction.
     * @param variable the variable whose domain has to be trimmed
     */
    @Override
    public void Trim2(FurnitureVariable variable) {
        Area modelArea = new Area(otherVariable.assignedValue.getArea());
        Area validArea = modelArea.rectangleAround(distance);
        variable.setValidOnly(validArea);
    }

    /**
     * Estimation of weight of this constraint.
     * @return 
     */
    @Override
    public int getWeight() {
        if (distance < 6) return 150;
        if (distance < 20) return 60;
        if (distance < 60) return 30;
        if (distance < 100) return 20;
        if (distance < 200) return 10;
        return 5;
    }

    /**
     * Checks whether two variables satisfy the constraint
     * @param assignedVariable The first variable
     * @param fvariable2 The second variable
     * @return 'true' if the two variables are separated by, at most, 'distance' cm. 'false' otherwise
     */
    @Override
    public boolean isSatisfied2(FurnitureVariable variable) {
        
        OrientedRectangle rectangle1 = ((FurnitureValue) otherVariable.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) variable.getAssignedValue()).getArea();
        
        return Area.distance(rectangle1, rectangle2) < distance;
    }
}
