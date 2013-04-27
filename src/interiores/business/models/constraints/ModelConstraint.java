/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints;

import interiores.business.models.FurnitureModel;

/**
 * Generic class for representing constraints over a furniture model
 * @author larribas
 */
public abstract class ModelConstraint {
    
    /**
     * Determines whether the model satisfies the constraint
     * @param model The specific piece of furniture to be checked
     * @return 'true' if the constraint is satisfied by 'model'. 'false' otherwise
     */
    public abstract boolean isSatisfied(FurnitureModel model);
    
}
