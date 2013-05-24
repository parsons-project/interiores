package interiores.business.models.constraints.binary;

import interiores.business.models.Orientation;
import interiores.business.models.OrientedRectangle;
import interiores.business.models.Room;
import interiores.business.models.backtracking.Area.Area;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.backtracking.InterioresVariable;
import interiores.business.models.FurnitureConstraints.BinaryConstraint;
import interiores.business.models.FurnitureConstraints.PreliminarTrimmer;

/**
 *
 * @author alvaro
 */
public class PartialFacingConstraint
    extends BinaryConstraint implements PreliminarTrimmer {
    
    private int maxDist;
    
    
    public PartialFacingConstraint() {
        
    }
    
    public PartialFacingConstraint(int distance) {
        maxDist = distance;
    }
    
    
    @Override
    public boolean isSatisfied2(FurnitureVariable variable) {
        OrientedRectangle rectangle1 = ((FurnitureValue) otherVariable.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) variable.getAssignedValue()).getArea();
        
        return rectangle1.enlarge(maxDist, rectangle1.getOrientation()).intersects(rectangle2) &&
               rectangle2.enlarge(maxDist, rectangle2.getOrientation()).intersects(rectangle1);
        
    }
    
    @Override
    public int getWeight() {
        return 110;
    }

    @Override
    public void Trim2(FurnitureVariable variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void preliminarTrim(FurnitureVariable variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
