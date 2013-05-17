/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.global;


import interiores.business.models.backtracking.Domain;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.constraints.GlobalConstraint;
import java.util.List;

/**
 * Represents a constraint that imposes a maximum budget for the wanted elements in a room.
 * This constraint is supposed to be updated with every change in the assignment status,
 * in the backtracking process.
 * @author larribas
 */
public class BudgetConstraint extends GlobalConstraint {
    
    private double max_budget;
    private double current_budget;
    
    public BudgetConstraint() {
    }

    public BudgetConstraint(float max_budg) {
        max_budget = max_budg;
        current_budget = 0;
    }
    
    @Override
    public boolean isSatisfied() {
        return current_budget <= max_budget;
    }

    @Override
    public void notifyAssignment(FurnitureValue fv) {
        current_budget += fv.getModel().getPrice();
    }

    @Override
    public void notifyUnassignment(FurnitureValue fv) {
        current_budget -= fv.getModel().getPrice();
    }

    @Override
    public void eliminateInvalidValues(List<Domain> domains) {
        
        // TODO Eliminate invalid values (due to the current budget)
        // Pending implementation. This method is intended to, for instance,
        // eliminate all the models of unassigned variables such that their
        // price exceeds the remaining budget.
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
