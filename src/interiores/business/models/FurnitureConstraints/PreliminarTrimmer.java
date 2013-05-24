/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.FurnitureConstraints;

import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureVariable;

/**
 * Eliminates values permanently from the domain of the variable.
 * Any aditional information that the trimmer needs to perform the trim
 * is stored as a field of the trimmer.
 * @author hector0193
 */
public interface PreliminarTrimmer
{
    public void preliminarTrim(FurnitureVariable variable);
    
    
}
