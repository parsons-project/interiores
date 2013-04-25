/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models;

import java.awt.Dimension;

/**
 * SizeConstraint represents a constraint imposed over the size of a furniture model.
 * @author larribas
 */
public class SizeConstraint {
    
    // minSize and maxSize determine the range within which the model's size should fall
    private Dimension minSize, maxSize;
    
    /**
     * Default constructor. Dimensions are initialized to zero.
     */
    public SizeConstraint() {
        
    }
    
    /**
     * Builds a SizeConstraint with specific minimum and maximum sizes
     * @param min The smallest size a model should have so as to satisfy the constraint
     * @param max The largest size a model should have so as to satisfy the constraint
     */
    public SizeConstraint(Dimension min, Dimension max) {
        minSize = min;
        maxSize = max;
    }
    
    /**
     * Determines whether a furniture model satisfies the constraint.
     * This occurs when the model size falls within the specified range.
     * For flexibility matters, a height or width of 0 is considered not to be a constraint
     * @param model The model whose size will be checked.
     * @return true if the model satisfies the size constraint; false otherwise
     */
    public boolean isSatisfied(FurnitureModel model) {
        Dimension modelSize = model.getSize();
        
        // In order to check whether the model satisfies the constraint, height and width components
        // are verified separately. If the maximum of a component is 0, it is considered not to
        // represent a constraint, and thus any value of that component will validate the constraint.
        boolean heightConstraint = maxSize.height == 0 ||
                                   minSize.height <= modelSize.height && modelSize.height <= maxSize.height;
        
        boolean widthConstraint = maxSize.width == 0 ||
                                   minSize.width <= modelSize.width && modelSize.width <= maxSize.width;
        
        return heightConstraint && widthConstraint;        
    }
        
}
