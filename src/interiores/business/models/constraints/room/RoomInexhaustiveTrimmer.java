/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.room;

import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.shared.backtracking.NoSolutionException;
import java.util.List;

/**
 * Represents a constraint imposed over a set of furniture (that is, the whole wishlist of a room)
 * @author larribas
 */
public interface RoomInexhaustiveTrimmer {
    
    /**
     * Given that the assignedVariables fulfil the constraint, returns whether
     * actual satisies it too.
     * We only need to check whether actual satisies the constraint, because
     * we assume that assignedVariables do not violate it, and unassignedVariables
     * can not violate it because they do not have a value yet.
     * @param assignedVariables
     * @param unassignedVariables
     * @param fixedFurniture
     * @param actual
     * @return 
     */
    public boolean isSatisfied(List<FurnitureVariable> assignedVariables,
            List<FurnitureVariable> unassignedVariables,
            List<FurnitureConstant> fixedFurniture,
            FurnitureVariable actual)
            throws NoSolutionException;

    public void notifyStepBack(List<FurnitureVariable> assignedVariables,
            List<FurnitureVariable> unassignedVariables,
            List<FurnitureConstant> fixedFurniture,
            FurnitureVariable actual, FurnitureValue actualValue);
   
 
}
