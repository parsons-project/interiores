package interiores.business.models.constraints.furniture.binary;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.Room;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.backtracking.InterioresVariable;
import interiores.business.models.constraints.furniture.BinaryConstraint;
import interiores.business.models.constraints.furniture.PreliminarTrimmer;

/**
 *
 * @author alvaro
 */
public class StraightFacingConstraint
    extends BinaryConstraint implements PreliminarTrimmer {
    
    private int maxDist = 1000;
    
    
    public StraightFacingConstraint(InterioresVariable otherVariable, int distance) {
        super(otherVariable);
        maxDist = distance;
    }
    
    
    @Override
    public boolean isSatisfied2(FurnitureVariable variable) {
        
        OrientedRectangle rectangle1 = ((FurnitureValue) otherVariable.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) variable.getAssignedValue()).getArea();
        
        return rectangle1.enlarge(maxDist, rectangle1.getOrientation()).intersects(rectangle2) &&
               rectangle2.enlarge(maxDist, rectangle2.getOrientation()).intersects(rectangle1) &&
               (rectangle1.getCenter().x == rectangle2.getCenter().x ||
                rectangle1.getCenter().y == rectangle2.getCenter().y);
        
    }
    
    @Override
    public int getWeight() {
        return 140;
    }

    @Override
    public void trim2(FurnitureVariable variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void preliminarTrim(FurnitureVariable variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}  
