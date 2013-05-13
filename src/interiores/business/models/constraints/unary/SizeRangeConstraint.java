package interiores.business.models.constraints.unary;

import interiores.business.models.FurnitureModel;
import interiores.business.models.backtracking.Domain;
import interiores.business.models.constraints.UnaryConstraint;
import interiores.utils.Dimension;
import interiores.utils.Range;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * SizeConstraint represents a constraint imposed over the size of a piece of furniture
 * @author larribas
 */
@XmlRootElement
abstract public class SizeRangeConstraint
    extends UnaryConstraint {
    
    @XmlElement
    private Dimension.Component component;
    
    @XmlElement
    private Range range;
    
    public SizeRangeConstraint(Dimension.Component component, Range range) {
        this.component = component;
        this.range = range;
    }
    
    public SizeRangeConstraint(Dimension.Component component, int minRange, int maxRange) {
        this(component, new Range(minRange, maxRange));
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
        if(range.max == 0)
            return true;
        
        return model.getSize().isBetween(component, range);  
    }
    
    
    /**
     * Eliminates models which do not satisfy the constraint.
     * @param variable The variable whose values have to be checked.
     */
    @Override
    public void eliminateInvalidValues(Domain domain) {
        Iterator it = domain.getModels(0).iterator();
        
        while(it.hasNext()) {
            FurnitureModel model = (FurnitureModel) it.next();
            
            if(! model.getSize().isBetween(component, range))
                it.remove();
        }
    }
    
    @Override
    public String toString() {
        return getClass().getName() + " Range[" + range + "]";
    }
}
