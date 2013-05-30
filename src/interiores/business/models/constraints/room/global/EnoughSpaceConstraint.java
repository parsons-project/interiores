/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.room.global;

import interiores.business.models.OrientedRectangle;
import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.RoomInexhaustiveTrimmer;
import interiores.business.models.constraints.room.RoomPreliminarTrimmer;
import interiores.shared.backtracking.NoSolutionException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Nil
 */
public class EnoughSpaceConstraint 
    extends GlobalConstraint implements RoomPreliminarTrimmer, RoomInexhaustiveTrimmer {

    private int totalArea;
    private int currentUsedArea;
    private int leastSpaceNeeded;
    private HashMap<String, Integer> minSpace;

    public EnoughSpaceConstraint(OrientedRectangle roomArea) {
        totalArea = roomArea.height * roomArea.width;
        currentUsedArea = 0;
    }
    
    
    @Override
    public boolean isSatisfied(List<FurnitureVariable> assignedVariables,
        List<FurnitureVariable> unassignedVariables,
        List<FurnitureConstant> fixedFurniture, FurnitureVariable actual) {
        OrientedRectangle area = actual.getAssignedValue().getArea();
        leastSpaceNeeded += area.height * area.width;
        leastSpaceNeeded -= minSpace.get(actual.getName());
        if (leastSpaceNeeded > totalArea) return false;
        
        return true;
    }

    /**
     * Initialize the leastSpaceNeeded and check that it is < than totalArea.
     * @param variables
     * @param fixedFurniture 
     */
    @Override
    public void preliminarTrim(List<FurnitureVariable> variables, List<FurnitureConstant> fixedFurniture)
        throws NoSolutionException {
        minSpace = new HashMap<String, Integer>();
        for (FurnitureVariable variable : variables) {
            minSpace.put(variable.getName(), variable.smallestModelSize());
            leastSpaceNeeded += variable.smallestModelSize();
        }
        for (FurnitureConstant constant : fixedFurniture) {
            OrientedRectangle area = constant.getAssignedValue().getArea();
            leastSpaceNeeded += area.height * area.width;
        }
        if (leastSpaceNeeded > totalArea) throw new NoSolutionException("Not enough space");
    }

    @Override
    public void notifyStepBack(List<FurnitureVariable> assignedVariables,
        List<FurnitureVariable> unassignedVariables,
        List<FurnitureConstant> fixedFurniture,
        FurnitureVariable actual, FurnitureValue actualValue) {
        OrientedRectangle area = actualValue.getArea();
        leastSpaceNeeded -= area.height * area.width;
        leastSpaceNeeded += minSpace.get(actual.getName());
    }


    
}
