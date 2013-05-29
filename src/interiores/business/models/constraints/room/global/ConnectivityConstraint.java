
package interiores.business.models.constraints.room.global;

import interiores.business.exceptions.ConstraintException;
import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.BacktrackingTimeTrimmer;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.RoomBacktrackingTimeTrimmer;
import interiores.business.models.constraints.room.RoomInexhaustiveTrimmer;
import interiores.shared.backtracking.NoSolutionException;
import java.util.List;

/**
 * This constraint checks that each furniture piece of the room is reacheable
 * from every door.
 * It is not implemented
 * @author Nil
 */
public class ConnectivityConstraint
    extends GlobalConstraint implements RoomBacktrackingTimeTrimmer {


    @Override
    public void trim(List<FurnitureVariable> assignedVariables,
            List<FurnitureVariable> unassignedVariables,
            List<FurnitureConstant> fixedFurniture,
            FurnitureVariable actual)
    {
        
    }

    @Override
    public boolean isSatisfied(List<FurnitureVariable> assignedVariables,
        List<FurnitureVariable> unassignedVariables, List<FurnitureConstant> fixedFurniture,
        FurnitureVariable actual)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notifyStepBack(List<FurnitureVariable> assignedVariables,
        List<FurnitureVariable> unassignedVariables,
        List<FurnitureConstant> fixedFurniture, FurnitureVariable actual, FurnitureValue actualValue)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
