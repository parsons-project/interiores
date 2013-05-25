
package interiores.business.models.constraints.room.global;

import interiores.business.exceptions.ConstraintException;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.BacktrackingTimeTrimmer;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.GlobalInexhaustiveConstraint;

/**
 * This constraint checks that each furniture piece of the room is reacheable
 * from every door.
 * It is not implemented
 * @author Nil
 */
public class ConnectivityConstraint
    extends GlobalConstraint implements BacktrackingTimeTrimmer {


    @Override
    public boolean isSatisfied(FurnitureVariable variable) {
        return true;
    }

    @Override
    public void trim(FurnitureVariable variable) {
        
    }
    
}
