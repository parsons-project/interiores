/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.global;

import interiores.business.exceptions.ConstraintException;
import interiores.business.models.backtracking.Domain;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.FurnitureConstraints.GlobalConstraint;
import java.awt.Color;
import java.util.List;

/**
 * Represents a global constraint imposed over the color of all the elements in a room.
 * This constraint forces all the furniture to be the same color, regardless of which 
 * specific color it is
 * @author larribas
 */
public class SameColorConstraint extends GlobalConstraint {

    private int assignments = 0;
    
    private Color current_color;
    
    public SameColorConstraint() { }
    

    @Override
    public void notifyAssignment(FurnitureValue fv) throws ConstraintException {
        
        if (assignments == 0) current_color = fv.getModel().getColor();
        else if ( current_color != fv.getModel().getColor() )
            throw new ConstraintException("Trying to assign a " + fv.getModel().getColor()
                    + " furniture to a room which can only contain " + current_color + " ones.");
        
        assignments++;
    }

    @Override
    public void notifyUnassignment(FurnitureValue fv) {
        
        assignments--;
    }

    @Override
    public void eliminateInvalidValues(List<Domain> domains) {
        
        // TODO Eliminate invalid values (due to the current color)
        // Pending implementation. This method is intended to, for instance,
        // eliminate all the models of unassigned variables such that their
        // color is different from the current color (as long as assigned > 0).
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
