/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.room.global;


import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.RoomBacktrackingTimeTrimmer;
import interiores.business.models.constraints.room.RoomPreliminarTrimmer;
import interiores.business.models.room.FurnitureModel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a constraint that imposes a maximum budget for the wanted elements in a room.
 * This constraint is supposed to be updated with every change in the assignment status,
 * in the backtracking process.
 * @author nmamano
 */
public class BudgetConstraint
    extends GlobalConstraint
    implements RoomPreliminarTrimmer, RoomBacktrackingTimeTrimmer {
    
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
    public boolean isSatisfied(List<FurnitureVariable> assignedVariables,
        List<FurnitureVariable> unassignedVariables,
        List<FurnitureConstant> fixedFurniture,
        FurnitureVariable actual)
    {
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
        double minBudget = 0;
        for (FurnitureVariable variable : variables)
            minBudget += variable.getMinPrice();
       
        for (FurnitureVariable variable : variables) {
            //if a model from this variable is more expensive than maxPrice,
            //there is no possible assignmentment to variables such that
            //variable has assigned this model and the maxBudget is not exceeded 
            double maxPrice = maxBudget - ( minBudget - variable.getMinPrice());
            eliminateTooExpensiveModels(variable, maxPrice);
        }
    }

    /**
     * Pre:
     * currentBudget is the sum of the prices of the variables of assignedVariables
     * plus thet price of actual.
     * 
     * For each unassigned variable, we trimP all values such that their price plus
     * the currentBudget plus the price of the cheapest model of other unassigned
     * variable is higher than maxBudget.
     * 
     * Not implemented. Not sure if this trimP is efficient.
     * 
     * @param assignedVariables
     * @param unassignedVariables
     * @param fixedFurniture
     * @param actual 
     */
    @Override
    public void trim(List<FurnitureVariable> assignedVariables,
        List<FurnitureVariable> unassignedVariables,
        List<FurnitureConstant> fixedFurniture,
        FurnitureVariable actual)
    {
        
    }
  
    private void eliminateTooExpensiveModels(FurnitureVariable variable, double maxPrice) {
        HashSet<FurnitureModel> validModels = new HashSet(variable.getDomain().getModels(0));
        Iterator<FurnitureModel> it = validModels.iterator();
        while (it.hasNext()) {
            if (it.next().getPrice() > maxPrice)
                it.remove();
        }
        
        variable.eliminateExceptM(validModels);
    }

}
