package interiores.business.models.constraints.room.global;

import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.PreliminarTrimmer;
import interiores.business.models.constraints.room.RoomBacktrackingTimeTrimmer;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.RoomPreliminarTrimmer;
import java.awt.Rectangle;
import java.util.List;

/**
 * This very important constraint ensures that the area of different variables
 * is disjoint; it also ensures that the passive area of variables is
 * respected.
 * 
 * @author nmamano
 */
public class SpaceRespectingConstraint
    extends GlobalConstraint
    implements RoomPreliminarTrimmer, RoomBacktrackingTimeTrimmer {
    
    /**
     * For each furniture variable, the area assigned to a furniture constant
     * or its passive space is eliminated.
     * @param variables
     * @param fixedFurniture 
     */
    @Override
    public void preliminarTrim(List<FurnitureVariable> variables, List<FurnitureConstant> fixedFurniture) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Pre: the space of all variables in the assignedVariable list has already
     * been trimmed.
     * For each unassigned variable, we trim the area of the actual variable.
     * @param assignedVariables
     * @param unassignedVariables
     * @param fixedFurniture
     * @param actual 
     */
    @Override
    public void trim(List<FurnitureVariable> assignedVariables, List<FurnitureVariable> unassignedVariables, List<FurnitureConstant> fixedFurniture, FurnitureVariable actual) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Pre: the space of all assigned variables respect each other.
     * We need to check whether actual respects the other assigned variables
     * and the other way around.
     * @param assignedVariables
     * @param unassignedVariables
     * @param fixedFurniture
     * @param actual
     * @return 
     */
    @Override
    public boolean isSatisfied(List<FurnitureVariable> assignedVariables, List<FurnitureVariable> unassignedVariables, List<FurnitureConstant> fixedFurniture, FurnitureVariable actual) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
