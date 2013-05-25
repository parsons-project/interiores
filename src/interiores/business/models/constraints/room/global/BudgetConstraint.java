/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.room.global;


import interiores.business.exceptions.ConstraintException;
import interiores.business.models.backtracking.Domain;
import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.room.GlobalBacktrackingTimeTrimmer;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.GlobalInexhaustiveConstraint;
import interiores.business.models.constraints.room.GlobalPreliminarTrimmer;
import interiores.core.business.BusinessException;
import java.util.List;

/**
 * Represents a constraint that imposes a maximum budget for the wanted elements in a room.
 * This constraint is supposed to be updated with every change in the assignment status,
 * in the backtracking process.
 * @author nmamano
 */
public class BudgetConstraint
    extends GlobalConstraint
    implements GlobalPreliminarTrimmer, GlobalBacktrackingTimeTrimmer {
    
    private double maxBudget;
    private double currentBudget;
    
    public BudgetConstraint() {
    }

    public BudgetConstraint(float maxBudget) {
        this.maxBudget = maxBudget;
        currentBudget = 0;
    }


    /**
     * Pre:
     * currentBudget is the sum of the prices of the variables of assignedVariables
     * @param assignedVariables
     * @param unassignedVariables
     * @param fixedFurniture
     * @param actual
     * @return 
     */
    @Override
    public boolean isSatisfied(List<FurnitureVariable> assignedVariables, List<FurnitureVariable> unassignedVariables, List<FurnitureConstant> fixedFurniture, FurnitureVariable actual) {
        currentBudget += actual.getAssignedValue().getModel().getPrice();
        return currentBudget <= maxBudget;
    }

    /**
     * For each variable, we eliminate all values such that their price plus
     * the price os the cheapest model of each other variable is higher than
     * maxBudget.
     * @param variables
     * @param fixedFurniture 
     */
    @Override
    public void preliminarTrim(List<FurnitureVariable> variables, List<FurnitureConstant> fixedFurniture) {
        //I implemented this somewhere else. I'll update when i come across it lols
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Pre:
     * currentBudget is the sum of the prices of the variables of assignedVariables
     * plus thet price of actual.
     * 
     * For each unassigned variable, we trim all values such that their price plus
     * the currentBudget plus the price of the cheapest model of other unassigned
     * variable is higher than maxBudget.
     * 
     * Not implemented. Not sure if this trim is efficient.
     * 
     * @param assignedVariables
     * @param unassignedVariables
     * @param fixedFurniture
     * @param actual 
     */
    @Override
    public void trim(List<FurnitureVariable> assignedVariables, List<FurnitureVariable> unassignedVariables, List<FurnitureConstant> fixedFurniture, FurnitureVariable actual) {
        
    }

    

//Deprecated methods and field: notify system not used.
    
//    @Override
//    public void notifyAssignment(FurnitureValue fv) throws ConstraintException {
//        if ( current_budget + fv.getModel().getPrice() <= max_budget )
//            current_budget += fv.getModel().getPrice();
//        else
//            throw new ConstraintException("Current budget (" + current_budget + "€) exceeds maximum budget ("
//                    + max_budget + "€)");
//    }
//
//    @Override
//    public void notifyUnassignment(FurnitureValue fv) {
//        current_budget -= fv.getModel().getPrice();
//    }
}
