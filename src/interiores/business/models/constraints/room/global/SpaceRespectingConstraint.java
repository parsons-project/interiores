package interiores.business.models.constraints.room.global;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.area.Area;
import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.RoomBacktrackingTimeTrimmer;
import interiores.business.models.constraints.room.RoomPreliminarTrimmer;
import interiores.core.Debug;
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
    implements RoomPreliminarTrimmer, RoomBacktrackingTimeTrimmer
{
    /**
     * Contains the room rectangle
     */
    private OrientedRectangle roomArea;
    
    public SpaceRespectingConstraint(OrientedRectangle roomArea) {
        this.roomArea = roomArea;
    }
    
    /**
     * For each furniture variable, the area assigned to a furniture constant
     * or its passive space is eliminated.
     * @param variables
     * @param fixedFurniture 
     */
    @Override
    public void preliminarTrim(List<FurnitureVariable> variables, List<FurnitureConstant> fixedFurniture) {
        for (FurnitureConstant fixed : fixedFurniture)
            for (FurnitureVariable variable : variables)
                variable.trimP(new Area(fixed.getAssignedValue().getWholeArea()));
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
    public void trim(List<FurnitureVariable> assignedVariables,
            List<FurnitureVariable> unassignedVariables,
            List<FurnitureConstant> fixedFurniture,
            FurnitureVariable actual) {
        
        for (FurnitureVariable variable : unassignedVariables) {
            Area invalidArea = new Area(actual.getAssignedValue().getWholeArea());
            variable.trimP(invalidArea);
        }
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
    public boolean isSatisfied(List<FurnitureVariable> assignedVariables,
        List<FurnitureVariable> unassignedVariables,
        List<FurnitureConstant> fixedFurniture,
        FurnitureVariable actual)
    {  
        FurnitureValue actualValue = actual.getAssignedValue();
        // A little explanation: fv.getArea() gets the ACTIVE area of actual_fv
        // while fv.getWholeArea() gets the PASSIVE + ACTIVE area of actual_fv
        
        //actual must be within the bounds of the room
        if (! roomArea.contains(actualValue.getWholeArea())) return false;

        for (FurnitureVariable variable : assignedVariables) {
            FurnitureValue otherValue = variable.getAssignedValue();
            
            if (actualValue.getArea().intersects(otherValue.getWholeArea()) ||
                actualValue.getWholeArea().intersects(otherValue.getArea()))
                return false;
        }
        return true;
    }
}
