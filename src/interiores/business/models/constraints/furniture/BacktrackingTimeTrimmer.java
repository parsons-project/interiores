/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.furniture;

import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureVariable;

/**
 * Trims values from the domain of the variable.
 * Any aditional information that the trimmer needs to perform the trim
 * is stored as a field of the trimmer.
 * @author nmamano
 */
public interface BacktrackingTimeTrimmer
    extends InexhaustiveTrimmer {
    
    public void trim(FurnitureVariable variable);
}
