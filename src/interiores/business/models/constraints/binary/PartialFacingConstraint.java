package interiores.business.models.constraints.binary;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.Room;
import interiores.business.models.backtracking.Area.Area;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.backtracking.InterioresVariable;
import interiores.business.models.constraints.BinaryConstraint;

/**
 *
 * @author alvaro
 */
public class PartialFacingConstraint extends BinaryConstraint{
    
    private int maxDist;
    
    
    public PartialFacingConstraint() {
        
    }
    
    public PartialFacingConstraint(int distance) {
        maxDist = distance;
    }
    
    
    @Override
    public boolean isSatisfied(InterioresVariable fvariable1, FurnitureVariable fvariable2) {
        OrientedRectangle rectangle1 = ((FurnitureValue) fvariable1.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) fvariable2.getAssignedValue()).getArea();
        
        return rectangle1.enlarge(maxDist, rectangle1.getOrientation()).intersects(rectangle2) &&
               rectangle2.enlarge(maxDist, rectangle2.getOrientation()).intersects(rectangle1);
        
    }
    
    @Override
    public int getWeight(OrientedRectangle roomArea) {
        return 110;
    }

    @Override
    public void trim(InterioresVariable assignedVariable, FurnitureVariable toTrimVariable, OrientedRectangle roomArea) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public void preliminarTrim(InterioresVariable variable1, InterioresVariable variable2, OrientedRectangle roomArea) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
