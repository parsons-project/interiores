/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models;

import interiores.utils.Size;

/**
 *
 * @author larribas
 */
public class SizeConstraint {
    
    Size minSize, maxSize;
    
    public boolean isSatisfied(FurnitureModel fm) {
        Size modelSize = fm.getSize();
        return modelSize.isLargerThan(minSize) && fm.getSize().isSmallerThan(maxSize);
    }
    
    
}
