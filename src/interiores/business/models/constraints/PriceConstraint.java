/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints;

import interiores.business.models.FurnitureModel;

/**
 * PriceConstraint represents a constraint imposed over the highest price a piece of furniture can cost
 * @author larribas
 */
public class PriceConstraint {
    
    // 'maxPrice' represents the topmost price a furniture model should cost in order to satisfy the constraint
    float maxPrice;
    
    /**
     * Creates a price constraint such that only those pieces of furniture which cost
     * 'maxPrice' or less will satisfy it
     * @param maxPrice The topmost price the constraint will set
     */
    public PriceConstraint(float maxPrice) {
        this.maxPrice = maxPrice;
    }
   
    
    /**
     * Determines whether a piece of furniture satisfies the constraint.
     * @param model The specific piece of furniture whose price is to be checked.
     * @return 'true' if the model costs <= than the price defined for the constraint. 'false' otherwise
     */
    public boolean isSatisfied(FurnitureModel model) {        
        return model.getPrice() <= maxPrice;
    }
    
    /**
     * Modifies the maximum price defined for the constraint
     * @param newMaxPrice 
     */
    public void changePrice(float newMaxPrice) {
        maxPrice = newMaxPrice;
    }
}
