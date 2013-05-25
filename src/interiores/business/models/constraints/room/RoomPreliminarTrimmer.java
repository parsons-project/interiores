/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.room;

import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureVariable;
import java.util.List;

/**
 * Eliminates values permanently from the domain of all variables.
 * Any aditional information that the trimmer needs to perform the trim
 * is stored as a field of the trimmer.
 * @author hector0193
 */
public interface RoomPreliminarTrimmer
{
    public void preliminarTrim(List<FurnitureVariable> variables,
            List<FurnitureConstant> fixedFurniture);
    
    
}
