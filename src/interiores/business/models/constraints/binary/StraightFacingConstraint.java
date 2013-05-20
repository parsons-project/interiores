package interiores.business.models.constraints.binary;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.InterioresVariable;
import interiores.business.models.constraints.BinaryConstraint;

/**
 *
 * @author alvaro
 */
public class StraightFacingConstraint extends BinaryConstraint {
    
    private int maxDist = 1000;
    
    
    public StraightFacingConstraint() {
        
    }
    
    public StraightFacingConstraint(int distance) {
        maxDist = distance;
    }
    
    
    @Override
    public boolean isSatisfied(InterioresVariable fvariable1, InterioresVariable fvariable2) {
        
        OrientedRectangle rectangle1 = ((FurnitureValue) fvariable1.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) fvariable2.getAssignedValue()).getArea();
        
        return rectangle1.enlarge(maxDist, rectangle1.getOrientation()).intersects(rectangle2) &&
               rectangle2.enlarge(maxDist, rectangle2.getOrientation()).intersects(rectangle1) &&
               (rectangle1.getCenter().x == rectangle2.getCenter().x ||
                rectangle1.getCenter().y == rectangle2.getCenter().y);
        
    }
    
    @Override
    public int getPriority() {
        return 5;
    }
}  