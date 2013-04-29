/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints;

import interiores.business.models.FurnitureModel;
import interiores.utils.Dimension;

/**
 * SizeConstraint represents a constraint imposed over the size of a piece of furniture
 * @author larribas
 */
public class SizeConstraint extends ModelConstraint {
    
    // minSize and maxSize determine the range within which the furniture's size should fall
    private Dimension minSize, maxSize;
        
    /**
     * Builds a SizeConstraint with specific minimum and maximum sizes
     * @param min The smallest size a piece of furniture should have so as to satisfy the constraint
     * @param max The largest size a piece of furniture should have so as to satisfy the constraint
     */
    public SizeConstraint(Dimension min, Dimension max) {
        minSize = min;
        maxSize = max;
    }
    
    /**
     * Determines whether a piece of furniture (a model) satisfies the constraint.
     * This occurs when the model size falls within the specified range.
     * For flexibility matters, a height or width of 0 is considered not to be a constraint
     * @param model The specific piece of furniture whose size will be checked.
     * @return 'true' if the model satisfies the size constraint; 'false' otherwise
     */
    public boolean isSatisfied(FurnitureModel model) {
        Dimension modelSize = model.getSize();
        
        // In order to check whether the model satisfies the constraint, height and width components
        // are verified separately. If the maximum of a component is 0, it is considered not to
        // represent a constraint, and thus any value of that component will validate the constraint.
        boolean heightConstraint = maxSize.depth == 0 ||
                                   minSize.depth <= modelSize.depth && modelSize.depth <= maxSize.depth;
        
        boolean widthConstraint = maxSize.width == 0 ||
                                   minSize.width <= modelSize.width && modelSize.width <= maxSize.width;
        
        return heightConstraint && widthConstraint;        
    }
    
        
}
