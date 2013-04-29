package interiores.business.models.constraints;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;

/**
 * 
 * @author alvaro
 */
public class MaxDistanceConstraint 
                extends BinaryConstraint {
    
    private int distance; // The maximum distance between the two variables
    
    public MaxDistanceConstraint(int distance) {
        this.distance = distance;
    }
    
    @Override
    public boolean isSatisfied(FurnitureVariable fvariable1, FurnitureVariable fvariable2) {
        
        OrientedRectangle rectangle1 = ((FurnitureValue) fvariable1.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) fvariable2.getAssignedValue()).getArea();
        
        return rectangle1.enlarge(distance).intersects(rectangle2);
    }
}
