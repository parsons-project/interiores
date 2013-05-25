/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.room;

import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureVariable;
import java.util.List;

/**
 * Trims values from the domain of unassigned variables, assuming that
 * the set of assignedVariables and the actual variable satisfy the constraint.
 * We only need to check trims
 * Any aditional information that the trimmer needs to perform the trim
 * is stored as a field of the trimmer.
 * @author nmamano
 */
public interface GlobalBacktrackingTimeTrimmer
    extends GlobalInexhaustiveConstraint {
    public void trim(List<FurnitureVariable> assignedVariables,
            List<FurnitureVariable> unassignedVariables,
            List<FurnitureConstant> fixedFurniture,
            FurnitureVariable actual);
}
