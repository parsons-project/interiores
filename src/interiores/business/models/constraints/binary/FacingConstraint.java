/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.binary;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.BinaryConstraint;

/**
 *
 * @author aesbu
 */
public class FacingConstraint extends BinaryConstraint{
    
    private int maxDist = 1000;
    
    
    public FacingConstraint() {
        
    }
    
    public FacingConstraint(int distance) {
        maxDist = distance;
    }
    
    
    @Override
    public boolean isSatisfied(FurnitureVariable fvariable1, FurnitureVariable fvariable2) {
        OrientedRectangle rectangle1 = ((FurnitureValue) fvariable1.getAssignedValue()).getArea();
        OrientedRectangle rectangle2 = ((FurnitureValue) fvariable2.getAssignedValue()).getArea();
        
        
        return rectangle1.enlarge(maxDist, rectangle1.getOrientation()).intersects(rectangle2) &&
               rectangle2.enlarge(maxDist, rectangle2.getOrientation()).intersects(rectangle1);
        
    }
    
}
