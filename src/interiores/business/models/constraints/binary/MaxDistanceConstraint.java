package interiores.business.models.constraints.binary;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.BinaryConstraint;
import interiores.utils.Dimension;
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
    
    /**
     * Checks whether two variables satisfy the constraint
     * @param fvariable1 The first variable
     * @param fvariable2 The second variable
     * @return 'true' if the two variables are separated by 'distance' cm. 'false' otherwise
     */
    @Override
    public boolean isSatisfied(FurnitureVariable fvariable1, FurnitureVariable fvariable2) {
        
        OrientedRectangle rectangle1 = ((FurnitureValue) fvariable1.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) fvariable2.getAssignedValue()).getArea();
        
        return (rectangle1.enlarge(new Dimension(distance,0)).intersects(rectangle2) ||
                rectangle1.enlarge(new Dimension(0, distance)).intersects(rectangle2) ||
                rectangle1.enlarge(new Dimension(distance/4, 3*distance/4)).intersects(rectangle2) ||
                rectangle1.enlarge(new Dimension(distance/2, distance/2)).intersects(rectangle2) ||
                rectangle1.enlarge(new Dimension(3 * distance/4, distance/4)).intersects(rectangle2));
    }
    
    @Override
    public String toString() {
        return "Maximum distance = " + distance + "cm constraint";
    }
    
}
