package interiores.business.models.constraints;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;

/**
 * This binary constraint represents 
 * @author alvaro
 */
public class MinDistanceConstraint 
                    extends BinaryConstraint {
    
    private int distance; // The minimum distance between the two variables
    
    public MinDistanceConstraint(int distance) {
        this.distance = distance;
    }
    
    @Override
    public boolean isSatisfied(FurnitureVariable fvariable1, FurnitureVariable fvariable2) {
        
        OrientedRectangle rectangle1 = ((FurnitureValue) fvariable1.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) fvariable2.getAssignedValue()).getArea();
        
        return !rectangle1.enlarge(distance).intersects(rectangle2);
    }
}
