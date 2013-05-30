
package interiores.business.models.constraints.room.global;

import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.RoomBacktrackingTimeTrimmer;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This constraint checks that each furniture piece of the room is reacheable
 * from every door.
 * It is not implemented
 * @author Nil
 */
@XmlRootElement
public class ConnectivityConstraint
    extends GlobalConstraint implements RoomBacktrackingTimeTrimmer {
    
    public ConnectivityConstraint()
    { }

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
