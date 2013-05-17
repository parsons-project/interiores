/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.global;

import interiores.business.exceptions.ConstraintException;
import interiores.business.models.backtracking.Domain;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.constraints.GlobalConstraint;
import java.util.List;

/**
 * Represents a global constraint imposed over the material of all the elements in a room.
 * This constraint forces all the furniture to be made in the same material, regardless 
 * of which specific material it is
 * @author larribas
 */
public class SameMaterialConstraint extends GlobalConstraint {

    private int assignments = 0;
    
    private String current_material;
    
    @Override
    public void notifyAssignment(FurnitureValue fv) throws ConstraintException {
        
        if (assignments == 0) current_material = fv.getModel().getMaterial();
        else if ( ! current_material.equals(fv.getModel().getMaterial()) )
            throw new ConstraintException("Trying to assign a furniture made in " + fv.getModel().getMaterial()
                    + " to a room whose furniture should be made in " + current_material);
        
        assignments++;
        
    }

    @Override
    public void notifyUnassignment(FurnitureValue fv) {
        assignments--;
    }

    @Override
    public void eliminateInvalidValues(List<Domain> domains) {
        
        // TODO Eliminate invalid values (due to the current material)
        // Pending implementation. This method is intended to, for instance,
        // eliminate all the models of unassigned variables such that their
        // material is different from the current material (as long as assigned > 0).
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
