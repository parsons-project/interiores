/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints;

import interiores.business.exceptions.ConstraintException;
import interiores.business.models.backtracking.Domain;
import interiores.business.models.backtracking.FurnitureValue;
import java.util.List;

/**
 * Represents a constraint imposed over a set of furniture (that is, the whole wishlist of a room)
 * @author larribas
 */
public abstract class GlobalConstraint {
    
    /**
     * Lets the constraint know of a value that's been assigned, so that it can update its information
     * @param fv The value that has been assigned
     */
    public abstract void notifyAssignment(FurnitureValue fv) throws ConstraintException;
    
    /**
     * Lets the constraint know of a value that's been unassigned, so that it can update its information
     * @param fv The value that has been unassigned
     */
    public abstract void notifyUnassignment(FurnitureValue fv);
    
    
    public abstract void eliminateInvalidValues(List<Domain> domains);
    
}
