/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints;

import interiores.business.models.FurnitureModel;
import interiores.business.models.backtracking.FurnitureVariable;
import java.util.Iterator;

/**
 * PriceConstraint represents a constraint imposed over the highest price a
 * piece of furniture can cost
 * @author larribas
 */
public class PriceConstraint
    extends UnaryConstraint {
    
    /**
     * 'maxPrice' represents the topmost price a furniture model should cost
     * inorder to satisfy the constraint
     */
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
     * Eliminates models which do not satisfy the constraint.
     * @param variable The variable whose values have to be checked.
     */
    @Override
    public void eliminateInvalidValues(FurnitureVariable variable) {
        Iterator it = variable.domainModels[0].iterator();
        while (it.hasNext()) {
            FurnitureModel model = (FurnitureModel) it.next();
            if (model.getPrice() > maxPrice)
                it.remove();
        }
    }
    
    
    /**
     * Modifies the maximum price defined for the constraint
     * @param newMaxPrice 
     */
    public void changePrice(float newMaxPrice) {
        maxPrice = newMaxPrice;
    }
}
