/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.unary;

import interiores.business.models.FurnitureModel;
import interiores.business.models.backtracking.Domain;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.utils.Dimension;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * SizeConstraint represents a constraint imposed over the size of a piece of furniture
 * @author larribas
 */
@XmlRootElement
public class SizeConstraint
    extends UnaryConstraint {
    
    /** 
     * minSize and maxSize determine the range within which the furniture's size
     * should fall.
     */
    @XmlElement
    private Dimension minSize;
    
    @XmlElement
    private Dimension maxSize;
    
    public SizeConstraint() {
        minSize = new Dimension();
        maxSize = new Dimension();
    }
    
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
     * @TODO DELETE THIS METHOD?
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
    
    
    /**
     * Eliminates models which do not satisfy the constraint.
     * @param variable The variable whose values have to be checked.
     */
    @Override
    public void eliminateInvalidValues(Domain domain) {
        Iterator it = domain.getModels(0).iterator();
        while (it.hasNext()) {
            FurnitureModel model = (FurnitureModel) it.next();
            
            Dimension modelSize = model.getSize();
        
            // In order to check whether the model satisfies the constraint, height and width components
            // are verified separately. If the maximum of a component is 0, it is considered not to
            // represent a constraint, and thus any value of that component will validate the constraint.
            boolean heightConstraint = maxSize.depth == 0 ||
                                       minSize.depth <= modelSize.depth && modelSize.depth <= maxSize.depth;

            boolean widthConstraint = maxSize.width == 0 ||
                                       minSize.width <= modelSize.width && modelSize.width <= maxSize.width;

            if (!heightConstraint || !widthConstraint) it.remove();
        }
    }
    
    
    
    public void changeWidth(int minWidth, int maxWidth) {
        minSize.width = minWidth;
        maxSize.width = maxWidth;
    }
    
    public void changeDepth(int minDepth, int maxDepth) {
        minSize.depth = minDepth;
        maxSize.depth = maxDepth;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");

        result.append(this.getClass().getName() + NEW_LINE);

        result.append("Minimum: " + minSize.toString() + NEW_LINE);
        result.append("Maximum: " + maxSize.toString() + NEW_LINE);
        return result.toString();
    }
    
}
